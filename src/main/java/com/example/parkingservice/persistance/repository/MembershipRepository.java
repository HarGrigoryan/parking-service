package com.example.parkingservice.persistance.repository;

import com.example.parkingservice.persistance.entity.Membership;
import com.example.parkingservice.persistance.entity.ResidentialCommunity;
import com.example.parkingservice.persistance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Membership findByUserAndResidentialCommunity(User user, ResidentialCommunity residentialCommunity);

    @Query("""
        SELECT m
        FROM Membership m
        WHERE m.status = 'ACTIVE'
        AND m.user.id = :userId
        AND m.residentialCommunity.id = :residentialCommunityId
        """)
    Optional<Membership> findActiveByUserIdAndResidentialCommunityId(Long userId, Long residentialCommunityId);
}
