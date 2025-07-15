package com.example.first_club.membership.enums;

public enum PlanDuration {
    MONTHLY("Monthly", 30),
    YEARLY("Yearly", 365);

    private final String displayName;
    private final int days;

    PlanDuration(String displayName, int days) {
        this.displayName = displayName;
        this.days = days;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getDays() {
        return days;
    }

    public boolean isMonthly() {
        return this == MONTHLY;
    }

    public boolean isYearly() {
        return this == YEARLY;
    }
}
