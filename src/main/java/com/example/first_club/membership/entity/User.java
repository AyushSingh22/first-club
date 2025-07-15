package com.example.first_club.membership.entity;

import com.example.first_club.membership.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserMembership> memberships;

    @Column(name = "total_spent", nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private Double totalSpent = 0.0;

    @Column(name = "email_verified", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean emailVerified = false;

    @Column(name = "phone_verified", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean phoneVerified = false;

    @PrePersist
    protected void onCreate() {
        super.onCreate();
        if (status == null) {
            status = UserStatus.ACTIVE;
        }
        if (totalSpent == null) {
            totalSpent = 0.0;
        }
        if (emailVerified == null) {
            emailVerified = false;
        }
        if (phoneVerified == null) {
            phoneVerified = false;
        }
    }
}