package com.example.first_club.membership.repository;

import com.example.first_club.membership.entity.User;
import com.example.first_club.membership.entity.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMembershipRepository extends JpaRepository<UserMembership, String> {
    Optional<UserMembership> findActiveMembershipByUserId(User userId);
}
