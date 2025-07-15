package com.example.first_club.membership.dto.response;

import com.example.first_club.membership.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanResponse {

    private String planId;
    private String planName;
    private BigDecimal price;
    private PlanDuration duration;
    private Map<String, Object> perks;
    private boolean isActive;
    private String description;
    private BigDecimal discountedPrice;
    private Integer discountPercentage;
}
