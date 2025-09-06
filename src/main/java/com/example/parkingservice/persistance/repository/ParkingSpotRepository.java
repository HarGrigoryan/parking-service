package com.example.parkingservice.persistance.repository;

import com.example.parkingservice.persistance.entity.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    @Query(nativeQuery = true,
    value = """
        SELECT *
        FROM parking_spot sP
        WHERE sP.number = :spotNumber
        AND sP.residential_community_id = :residentialCommunityId
        """)
    ParkingSpot findBySpotNumberAndResidentialCommunityId(String spotNumber, Long residentialCommunityId);
}
