package com.example.first_club.membership.entity;

import com.example.first_club.membership.enums.MembershipStatus;
import com.example.first_club.membership.enums.TierLevel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "membership_tiers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipTier extends BaseEntity {

    @Column(name = "tier_name", nullable = false, unique = true)
    private String tierName;

    @Enumerated(EnumType.STRING)
    @Column(name = "tier_level", nullable = false)
    private TierLevel tierLevel;

    @Column(name = "description")
    private String description;

    @Column(name = "min_spend_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal minSpendAmount;

    @Column(name = "max_spend_amount", precision = 10, scale = 2)
    private BigDecimal maxSpendAmount;

    @Column(name = "additional_discount", precision = 5, scale = 2)
    private BigDecimal additionalDiscount;

    @Column(name = "cashback_percentage", precision = 5, scale = 2)
    private BigDecimal cashbackPercentage;

    @Column(name = "free_delivery_threshold", precision = 10, scale = 2)
    private BigDecimal freeDeliveryThreshold;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "benefits", columnDefinition = "JSON")
    private Map<String, Object> benefits;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "upgrade_criteria", columnDefinition = "JSON")
    private Map<String, Object> upgradeCriteria;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MembershipStatus status;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @OneToMany(mappedBy = "membershipTier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserMembership> userMemberships;

    @ManyToOne
    @JoinColumn(name = "membership_plan_id")
    private MembershipPlan membershipPlan;


    @PrePersist
    protected void onCreate() {
        super.onCreate();
        if (status == null) {
            status = MembershipStatus.ACTIVE;
        }
    }
}
