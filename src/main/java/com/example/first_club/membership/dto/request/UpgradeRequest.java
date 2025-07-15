package com.example.first_club.membership.dto.request;

import com.example.first_club.membership.entity.MembershipTier;
import com.example.first_club.membership.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpgradeRequest {

    @NotNull(message = "Target tier is required")
    private MembershipTier targetTier;

    @NotNull(message = "User ID is required")
    private User userId;

    @NotBlank(message = "Payment method ID is required")
    private String paymentMethodId;

    private Boolean immediateUpgrade = true;
}