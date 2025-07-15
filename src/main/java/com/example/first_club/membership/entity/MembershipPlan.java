package com.example.first_club.membership.entity;


import com.example.first_club.membership.enums.MembershipStatus;
import com.example.first_club.membership.enums.PlanDuration;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "membership_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipPlan extends BaseEntity {

    @Column(name = "plan_name", nullable = false, unique = true)
    private String planName;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "plan_type", nullable = false)
    private PlanDuration planDuration;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "duration_days", nullable = false)
    private Integer durationDays;

    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @Column(name = "free_delivery", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean freeDelivery = false;

    @Column(name = "priority_support", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean prioritySupport = false;

    @Column(name = "early_access", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean earlyAccess = false;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "perks", columnDefinition = "JSON")
    private Map<String, Object> perks;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MembershipStatus status;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @OneToMany(mappedBy = "membershipPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserMembership> userMemberships;

    @PrePersist
    protected void onCreate() {
        super.onCreate();
        if (status == null) {
            status = MembershipStatus.ACTIVE;
        }
        if (freeDelivery == null) {
            freeDelivery = false;
        }
        if (prioritySupport == null) {
            prioritySupport = false;
        }
        if (earlyAccess == null) {
            earlyAccess = false;
        }
    }
}
