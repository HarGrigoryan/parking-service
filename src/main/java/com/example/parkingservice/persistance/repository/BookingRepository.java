package com.example.parkingservice.persistance.repository;

import com.example.parkingservice.criteria.BookingSearchCriteria;
import com.example.parkingservice.dto.BookingResponseDTO;
import com.example.parkingservice.persistance.entity.Booking;
import com.example.parkingservice.persistance.entity.ParkingSpot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByParkingSpot(ParkingSpot parkingSpot);


    @Query("""
    SELECT new com.example.parkingservice.dto.BookingResponseDTO(
        b.id, b.startTime, b.endTime, b.user.id, b.parkingSpot.id,
             b.createdAt, b.updatedAt, b.status)
    FROM Booking b
    WHERE (b.startTime >= :#{#criteria.startTime})
    AND (b.endTime <= :#{#criteria.endTime})
    AND (b.updatedAt >= :#{#criteria.updatedAt})
    AND (b.createdAt >= :#{#criteria.createdAt})
    AND (:#{#criteria.userId} IS NULL OR b.user.id = :#{#criteria.userId})
    AND (:#{#criteria.parkingSpotId} IS NULL OR b.parkingSpot.id = :#{#criteria.parkingSpotId})
    AND (:#{#criteria.status} IS NULL OR b.status = :#{#criteria.status})
    """)
    Page<BookingResponseDTO> findAll(BookingSearchCriteria criteria, Pageable pageable);
}
