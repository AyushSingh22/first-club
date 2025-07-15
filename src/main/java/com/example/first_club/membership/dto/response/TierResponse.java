package com.example.first_club.membership.dto.response;

import com.example.first_club.membership.enums.MembershipStatus;
import com.example.first_club.membership.enums.TierLevel;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class TierResponse {

    private String tierId;
    private String tierName;
    private TierLevel tierLevel;
    private String description;
    private BigDecimal minSpendAmount;
    private BigDecimal maxSpendAmount;
    private BigDecimal additionalDiscount;
    private BigDecimal cashbackPercentage;
    private BigDecimal freeDeliveryThreshold;
    private Map<String, Object> benefits;
    private Map<String, Object> upgradeCriteria;
    private MembershipStatus status;
    private Integer sortOrder;
}
