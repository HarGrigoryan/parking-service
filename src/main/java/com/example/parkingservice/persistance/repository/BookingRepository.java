package com.example.parkingservice.persistance.repository;

import com.example.parkingservice.persistance.entity.Booking;
import com.example.parkingservice.persistance.entity.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByParkingSpot(ParkingSpot parkingSpot);
}
