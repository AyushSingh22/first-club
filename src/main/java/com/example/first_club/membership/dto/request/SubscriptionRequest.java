package com.example.first_club.membership.dto.request;

import com.example.first_club.membership.enums.PlanDuration;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequest {

    @NotNull(message = "User ID is required")
    private String userId;

    @NotNull(message = "Plan ID is required")
    @Positive(message = "Plan ID must be positive")
    private String planId;

    @NotNull(message = "Plan duration is required")
    private PlanDuration duration;

    @NotBlank(message = "Payment method ID is required")
    private String paymentMethodId;

    private String couponCode;

    @NotNull(message = "Auto renewal preference is required")
    private Boolean autoRenewal = true;;
}
