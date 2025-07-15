package com.example.first_club.membership.dto.response;

import com.example.first_club.membership.entity.MembershipTier;
import com.example.first_club.membership.enums.MembershipStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipResponse {

    private String membershipId;
    private String planName;
    private MembershipTier tier;
    private MembershipStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean autoRenewal;
    private Map<String, Object> perks;
    private Double totalSpent;
    private Integer daysRemaining;
    private String nextBillingDate;
    private boolean canUpgrade;
    private boolean canDowngrade;
}
