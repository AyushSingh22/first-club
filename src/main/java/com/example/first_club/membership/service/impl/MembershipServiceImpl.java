package com.example.first_club.membership.service.impl;


import com.example.first_club.membership.dto.request.*;
import com.example.first_club.membership.dto.response.*;
import com.example.first_club.membership.entity.*;
import com.example.first_club.membership.enums.*;
import com.example.first_club.membership.repository.MembershipPlanRepository;
import com.example.first_club.membership.repository.MembershipTierRepository;
import com.example.first_club.membership.repository.UserMembershipRepository;
import com.example.first_club.membership.repository.UserRepository;
import com.example.first_club.membership.service.MembershipService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final UserRepository userRepository;
    private final MembershipPlanRepository planRepository;
    private final MembershipTierRepository tierRepository;
    private final UserMembershipRepository userMembershipRepository;

    @Transactional
    @Override
    public UserMembershipResponse subscribeToPlan(SubscriptionRequest request) {
        log.debug("Subscribing user={} to plan={}", request.getUserId(), request.getPlanId());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        MembershipPlan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));

        MembershipTier tier = Optional.ofNullable(
                plan.getPlanDuration() == PlanDuration.YEARLY
                        ? tierRepository.findByTierLevel(TierLevel.GOLD)
                        : tierRepository.findByTierLevel(TierLevel.SILVER)
        ).orElseThrow(() -> new IllegalStateException("No suitable tier configured in the system"));

        UserMembership membership = buildMembership(request, user, plan, tier);
        userMembershipRepository.save(membership);
        
        log.info("User [{}] subscribed successfully to plan [{}]", user.getId(), plan.getPlanName());
        return mapToUserMembershipResponse(membership);
    }

    @Transactional
    @Override
    public UserMembershipResponse upgradeMembershipTier(UpgradeRequest request) {
        log.info("Upgrading user={} to tier={}", request.getUserId(), request.getTargetTier().getTierName());

        UserMembership membership = userMembershipRepository.findActiveMembershipByUserId(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Active membership not found for user"));

        MembershipTier targetTier = tierRepository.findById(request.getTargetTier().getId())
                .orElseThrow(() -> new IllegalArgumentException("Target tier not found"));

        if (membership.getUser().getTotalSpent() < targetTier.getMinSpendAmount().doubleValue()) {
            throw new IllegalArgumentException("User does not meet the minimum spend amount for upgrade to tier: "
                    + targetTier.getTierName());
        }
        membership.setMembershipTier(targetTier);
        MembershipPlan associatedPlan = targetTier.getMembershipPlan();
        if (associatedPlan == null) {
            throw new IllegalStateException("No associated membership plan found for the selected tier");
        }

        membership.setStatus(MembershipStatus.ACTIVE);
        userMembershipRepository.save(membership);

        return getUserMembershipResponse(membership, associatedPlan, targetTier);
    }

    @Transactional
    @Override
    public UserMembershipResponse downgradeMembershipTier(DowngradeRequest request) {
        log.debug("Downgrading user={} to tier={}", request.getUserId(), request.getTargetTier());

        UserMembership membership = userMembershipRepository.findActiveMembershipByUserId(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Active membership not found for user"));

        membership.setMembershipTier(request.getTargetTier());
        membership.setStatus(MembershipStatus.ACTIVE);
        userMembershipRepository.save(membership);

        return mapToUserMembershipResponse(membership);
    }

    @Transactional
    @Override
    public void cancelSubscription(CancelSubscriptionRequest request) {
        log.debug("Cancelling subscription for user={} reason={}", request.getUserId(), request.getReason());

        UserMembership membership = userMembershipRepository.findActiveMembershipByUserId(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Active membership not found for user"));

        membership.setStatus(MembershipStatus.CANCELLED);
        membership.setCancelledAt(LocalDate.now().atStartOfDay());
        membership.setCancelledReason(request.getReason());
        userMembershipRepository.save(membership);
    }

    @Override
    public MembershipResponse getMembershipDetails(String userId) {
        UserMembership membership = userMembershipRepository.findActiveMembershipByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No active membership found for user"));

        return mapToMembershipResponse(membership);
    }

    @Override
    public PlansAndTiersResponse getAllPlansAndTiers() {
        List<PlanResponse> plans = planRepository.findByStatus(MembershipStatus.ACTIVE).stream()
                .map(this::mapToPlanResponse)
                .collect(Collectors.toList());

        List<TierResponse> tiers = tierRepository.findAll().stream()
                .map(this::mapToTierResponse)
                .collect(Collectors.toList());

        return PlansAndTiersResponse.builder()
                .plans(plans)
                .tiers(tiers)
                .build();
    }

    private static UserMembership buildMembership(SubscriptionRequest request, User user, MembershipPlan plan, MembershipTier tier) {
        return UserMembership.builder()
                .user(user)
                .membershipPlan(plan)
                .membershipTier(tier)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(plan.getDurationDays()))
                .status(MembershipStatus.ACTIVE)
                .autoRenewal(request.getAutoRenewal())
                .amountPaid(plan.getPrice())
                .paymentMethodId(request.getPaymentMethodId())
                .build();
    }

    // Utility Mapping methods
    private UserMembershipResponse mapToUserMembershipResponse(UserMembership membership) {
        return UserMembershipResponse.builder()
                .membershipId(membership.getId())
                .userId(membership.getUser().getId())
                .userEmail(membership.getUser().getEmail())
                .planName(membership.getMembershipPlan().getPlanName())
                .tierName(membership.getMembershipTier().getTierName())
                .tierLevel(membership.getMembershipTier().getTierLevel())
                .startDate(membership.getStartDate())
                .endDate(membership.getEndDate())
                .status(membership.getStatus())
                .amountPaid(membership.getAmountPaid())
                .autoRenewal(membership.getAutoRenewal())
                .build();
    }

    private MembershipResponse mapToMembershipResponse(UserMembership membership) {
        return MembershipResponse.builder()
                .membershipId(membership.getId())
                .planName(membership.getMembershipPlan().getPlanName())
                .tier(membership.getMembershipTier())
                .status(membership.getStatus())
                .startDate(membership.getStartDate().atStartOfDay())
                .endDate(membership.getEndDate().atStartOfDay())
                .autoRenewal(membership.getAutoRenewal())
                .build();
    }

    private PlanResponse mapToPlanResponse(MembershipPlan plan) {
        return PlanResponse.builder()
                .planId(plan.getId())
                .planName(plan.getPlanName())
                .price(plan.getPrice())
                .duration(plan.getPlanDuration() == PlanDuration.YEARLY ? PlanDuration.YEARLY : PlanDuration.MONTHLY)
                .perks(plan.getPerks())
                .isActive(plan.getStatus() == MembershipStatus.ACTIVE)
                .description(plan.getDescription())
                .build();
    }

    private TierResponse mapToTierResponse(MembershipTier tier) {
        return TierResponse.builder()
                .tierId(tier.getId())
                .tierName(tier.getTierName())
                .tierLevel(tier.getTierLevel())
                .description(tier.getDescription())
                .benefits(tier.getBenefits())
                .build();
    }

    private UserMembershipResponse getUserMembershipResponse(UserMembership membership, MembershipPlan associatedPlan, MembershipTier targetTier) {
        return UserMembershipResponse.builder()
                .membershipId(membership.getId())
                .userId(membership.getUser().getId())
                .userEmail(membership.getUser().getEmail())
                .planName(associatedPlan.getPlanName())
                .tierName(targetTier.getTierName())
                .tierLevel(targetTier.getTierLevel())
                .startDate(membership.getStartDate())
                .endDate(membership.getEndDate())
                .nextBillingDate(membership.getNextBillingDate())
                .status(membership.getStatus())
                .amountPaid(membership.getAmountPaid())
                .autoRenewal(membership.getAutoRenewal())
                .perks(associatedPlan.getPerks())
                .benefits(targetTier.getBenefits())
                .freeDelivery(associatedPlan.getFreeDelivery())
                .prioritySupport(associatedPlan.getPrioritySupport())
                .earlyAccess(associatedPlan.getEarlyAccess())
                .discountPercentage(associatedPlan.getDiscountPercentage())
                .cashbackPercentage(targetTier.getCashbackPercentage())
                .daysUntilExpiry(calculateDaysRemaining(membership.getEndDate()))
                .isExpiringSoon(isExpiringSoon(membership.getEndDate()))
                .build();
    }

    private int calculateDaysRemaining(LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(LocalDate.now(), endDate);
    }

    private boolean isExpiringSoon(LocalDate endDate) {
        return LocalDate.now().plusDays(7).isAfter(endDate);
    }
}

