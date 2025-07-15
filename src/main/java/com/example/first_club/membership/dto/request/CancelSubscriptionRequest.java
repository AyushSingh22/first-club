package com.example.first_club.membership.dto.request;

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
public class CancelSubscriptionRequest {

    @NotNull(message = "User ID is required")
    private User userId;

    @NotBlank(message = "Cancellation reason is required")
    private String reason;

    private Boolean immediateCancel = false;
}
