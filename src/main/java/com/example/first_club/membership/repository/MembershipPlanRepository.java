package com.example.first_club.membership.repository;

import com.example.first_club.membership.entity.MembershipPlan;
import com.example.first_club.membership.enums.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, String> {
    List<MembershipPlan> findByStatus(MembershipStatus status);
}
