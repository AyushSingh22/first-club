package com.example.first_club.membership.controller;

import com.example.first_club.membership.dto.request.*;
import com.example.first_club.membership.dto.response.*;
import com.example.first_club.membership.entity.User;
import com.example.first_club.membership.service.MembershipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/memberships")
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    /**
     * Subscribe to memebership plan
     */
    @PostMapping("/subscribe")
    public ResponseEntity<ApiResponse<UserMembershipResponse>> subscribeToPlan(
            @Valid @RequestBody SubscriptionRequest request) {

        log.info("Subscribing user [{}] to plan [{}]", request.getUserId(), request.getPlanId());
        UserMembershipResponse response = membershipService.subscribeToPlan(request);
        return ResponseEntity.ok(ApiResponse.success("Subscription successful", response));
    }

    /**
     * Upgrade user membership tier
     */
    @PostMapping("/upgrade")
    public ResponseEntity<ApiResponse<UserMembershipResponse>> upgradeMembership(
            @Valid @RequestBody UpgradeRequest request) {

        log.info("Upgrading user [{}] to tier [{}]", request.getUserId(), request.getTargetTier());
        UserMembershipResponse response = membershipService.upgradeMembershipTier(request);
        return ResponseEntity.ok(ApiResponse.success("Upgrade successful", response));
    }

    /**
     * Downgrade user membership tier
     */
    @PostMapping("/downgrade")
    public ResponseEntity<ApiResponse<UserMembershipResponse>> downgradeMembership(
            @Valid @RequestBody DowngradeRequest request) {

        log.info("Downgrading user [{}] to tier [{}]", request.getUserId(), request.getTargetTier());
        UserMembershipResponse response = membershipService.downgradeMembershipTier(request);
        return ResponseEntity.ok(ApiResponse.success("Downgrade successful", response));
    }

    /**
     * Cancel user subscription
     */
    @PostMapping("/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelSubscription(
            @Valid @RequestBody CancelSubscriptionRequest request) {

        log.info("Cancelling subscription for user [{}] with reason: {}", request.getUserId(), request.getReason());
        membershipService.cancelSubscription(request);
        return ResponseEntity.ok(ApiResponse.success("Subscription cancelled successfully", null));
    }

    /**
     * Get current membership details for a user
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<MembershipResponse>> getMembershipDetails(
            @PathVariable User userId) {

        log.info("Fetching membership details for user [{}]");
        MembershipResponse response = membershipService.getMembershipDetails(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Fetch all available membership plans and tiers
     */
    @GetMapping("/plans-tiers")
    public ResponseEntity<ApiResponse<PlansAndTiersResponse>> getAvailablePlansAndTiers() {

        log.info("Fetching all available membership plans and tiers");
        PlansAndTiersResponse response = membershipService.getAllPlansAndTiers();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
