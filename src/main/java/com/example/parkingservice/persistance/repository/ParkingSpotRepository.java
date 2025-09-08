package com.example.parkingservice.persistance.repository;

import com.example.parkingservice.criteria.ParkingSpotSearchCriteria;
import com.example.parkingservice.dto.ParkingSpotResponseDTO;
import com.example.parkingservice.persistance.entity.ParkingSpot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("""
        SELECT new com.example.parkingservice.dto.ParkingSpotResponseDTO(
                p.id, p.spotNumber, p.type, p.status, p.residentialCommunity.id
                )
        FROM ParkingSpot p
        WHERE (:#{#searchCriteria.spotNumber} IS  NULL OR p.spotNumber = :#{#searchCriteria.spotNumber})
        AND (:#{#searchCriteria.status} IS NULL OR p.status = :#{#searchCriteria.status})
        AND (:#{#searchCriteria.type} IS NULL OR p.type = :#{#searchCriteria.type})
        AND (:#{#searchCriteria.residentialCommunityId} IS NULL OR p.residentialCommunity.id = :#{#searchCriteria.residentialCommunityId})
        AND (:#{#searchCriteria.onlyAvailable} IS NULL OR :#{#searchCriteria.onlyAvailable} IS FALSE
             OR NOT EXISTS (
                     SELECT b FROM Booking b
                     WHERE b.parkingSpot.id = p.id
                     AND (b.status = 'BOOKED' OR b.status = 'PARKED')
                     AND b.startTime <=  :#{#searchCriteria.bufferedEndTime}
                     AND b.endTime   >=  :#{#searchCriteria.bufferedStartTime}
             ))
        """)
    Page<ParkingSpotResponseDTO> findAll(ParkingSpotSearchCriteria searchCriteria, Pageable pageable);
}
