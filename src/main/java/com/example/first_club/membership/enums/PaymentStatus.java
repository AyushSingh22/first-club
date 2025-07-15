package com.example.first_club.membership.enums;

public enum PaymentStatus {
    PENDING("Pending"),
    SUCCESS("Success"),
    FAILED("Failed"),
    REFUNDED("Refunded"),
    PARTIALLY_REFUNDED("Partially Refunded");

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isSuccess() {
        return this == SUCCESS;
    }

    public boolean isFailed() {
        return this == FAILED;
    }

    public boolean canRefund() {
        return this == SUCCESS;
    }
}
