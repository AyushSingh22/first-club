package com.example.first_club.membership.service;


import com.example.first_club.membership.dto.request.*;
import com.example.first_club.membership.dto.response.*;
import com.example.first_club.membership.entity.User;

public interface MembershipService {

    UserMembershipResponse subscribeToPlan(SubscriptionRequest request);

    UserMembershipResponse upgradeMembershipTier(UpgradeRequest request);

    UserMembershipResponse downgradeMembershipTier(DowngradeRequest request);

    void cancelSubscription(CancelSubscriptionRequest request);

    MembershipResponse getMembershipDetails(String userId);

    PlansAndTiersResponse getAllPlansAndTiers();
}
