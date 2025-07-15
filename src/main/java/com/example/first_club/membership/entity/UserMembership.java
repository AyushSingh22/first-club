package com.example.first_club.membership.entity;

import com.example.first_club.membership.enums.MembershipStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_memberships")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMembership extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_plan_id", nullable = false)
    private MembershipPlan membershipPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_tier_id", nullable = false)
    private MembershipTier membershipTier;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "next_billing_date")
    private LocalDate nextBillingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MembershipStatus status;

    @Column(name = "amount_paid", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountPaid;

    @Column(name = "auto_renewal", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean autoRenewal = true;

    @Column(name = "payment_method_id")
    private String paymentMethodId;

    @Column(name = "subscription_id")
    private String subscriptionId;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "cancelled_reason")
    private String cancelledReason;

    @Column(name = "upgraded_from_id")
    private Long upgradedFromId;

    @Column(name = "downgraded_from_id")
    private Long downgradedFromId;

    @Column(name = "prorated_amount", precision = 10, scale = 2)
    private BigDecimal proratedAmount;

    @PrePersist
    protected void onCreate() {
        super.onCreate();
        if (status == null) {
            status = MembershipStatus.ACTIVE;
        }
        if (autoRenewal == null) {
            autoRenewal = true;
        }
    }

    public boolean isActive() {
        return status == MembershipStatus.ACTIVE &&
                (endDate == null || endDate.isAfter(LocalDate.now()));
    }

    public boolean isExpired() {
        return endDate != null && endDate.isBefore(LocalDate.now());
    }

    public boolean isExpiringSoon(int days) {
        return endDate != null &&
                endDate.isAfter(LocalDate.now()) &&
                endDate.isBefore(LocalDate.now().plusDays(days));
    }
}