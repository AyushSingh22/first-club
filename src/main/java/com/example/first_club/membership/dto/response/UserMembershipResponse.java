package com.example.first_club.membership.dto.response;

import com.example.first_club.membership.enums.*;
import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
public class UserMembershipResponse {

    private String  membershipId;
    private String userId;
    private String userEmail;
    private String planName;
    private String tierName;
    private TierLevel tierLevel;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate nextBillingDate;
    private MembershipStatus status;
    private BigDecimal amountPaid;
    private Boolean autoRenewal;
    private Map<String, Object> perks;
    private Map<String, Object> benefits;
    private Boolean freeDelivery;
    private Boolean prioritySupport;
    private Boolean earlyAccess;
    private BigDecimal discountPercentage;
    private BigDecimal cashbackPercentage;
    private Integer daysUntilExpiry;
    private Boolean isExpiringSoon;
}

