package com.example.first_club.membership.enums;


public enum TierLevel {
    SILVER("Silver", 1, 0),
    GOLD("Gold", 2, 10000),
    PLATINUM("Platinum", 3, 50000);

    private final String displayName;
    private final int level;
    private final double minSpendRequired;

    TierLevel(String displayName, int level, double minSpendRequired) {
        this.displayName = displayName;
        this.level = level;
        this.minSpendRequired = minSpendRequired;
    }

    public boolean canUpgradeTo(TierLevel targetTier) {
        return this.level < targetTier.level;
    }

    public boolean canDowngradeTo(TierLevel targetTier) {
        return this.level > targetTier.level;
    }

    public static TierLevel getByLevel(int level) {
        for (TierLevel tier : values()) {
            if (tier.level == level) {
                return tier;
            }
        }
        throw new IllegalArgumentException("Invalid tier level: " + level);
    }
}