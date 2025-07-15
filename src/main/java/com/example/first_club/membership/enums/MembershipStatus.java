package com.example.first_club.membership.enums;

import lombok.Getter;

@Getter
public enum MembershipStatus {
    ACTIVE("Active"),
    EXPIRED("Expired"),
    CANCELLED("Cancelled"),
    SUSPENDED("Suspended"),
    PENDING_PAYMENT("Pending");

    private final String displayName;

    MembershipStatus(String displayName) {
        this.displayName = displayName;
    }

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean canUpgrade() {
        return this == ACTIVE;
    }

    public boolean canDowngrade() {
        return this == ACTIVE;
    }

}