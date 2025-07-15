package com.example.first_club.membership.repository;

import com.example.first_club.membership.entity.MembershipTier;
import com.example.first_club.membership.enums.TierLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipTierRepository extends JpaRepository<MembershipTier, String> {
    MembershipTier findByTierLevel(TierLevel tierLevel);

}
