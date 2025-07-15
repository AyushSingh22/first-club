package com.example.first_club.membership.dto.request;

import com.example.first_club.membership.entity.MembershipTier;
import com.example.first_club.membership.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DowngradeRequest {

    @NotNull(message = "User ID is required")
    private User userId;

    @NotNull(message = "Target tier is required")
    private MembershipTier targetTier;

    private Boolean immediateDowngrade = false;

    private String reason;
}
