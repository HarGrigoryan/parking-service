package com.example.parkingservice.service;

import com.example.parkingservice.dto.BookingRequestDTO;
import com.example.parkingservice.dto.BookingResponseDTO;
import com.example.parkingservice.enums.BookingStatus;
import com.example.parkingservice.exception.InvalidArgumentException;
import com.example.parkingservice.exception.ResourceDoesNotExistException;
import com.example.parkingservice.persistance.entity.Booking;
import com.example.parkingservice.persistance.entity.ParkingSpot;
import com.example.parkingservice.persistance.repository.BookingRepository;
import com.example.parkingservice.persistance.repository.ParkingSpotRepository;
import com.example.parkingservice.persistance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ParkingSpotRepository parkingSpotRepository;
    private final UserRepository userRepository;

    public BookingResponseDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceDoesNotExistException(id, "Booking"));
        return  BookingResponseDTO.from(booking);
    }

    public BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO) {
        Long parkingSpotId = bookingRequestDTO.getParkingSpotId();
        ParkingSpot parkingSpot = parkingSpotRepository.findById(parkingSpotId).orElseThrow(() ->
                new ResourceDoesNotExistException(parkingSpotId, "ParkingSpot"));
        checkRequestedInterval(bookingRequestDTO, parkingSpot);
        Booking booking = new Booking();
        configureBooking(booking, parkingSpot, bookingRequestDTO, BookingStatus.BOOKED);
        return BookingResponseDTO.from(bookingRepository.save(booking));
    }

    public BookingResponseDTO updateBooking(BookingRequestDTO bookingRequestDTO, Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceDoesNotExistException(id, "Booking"));
        Long parkingSpotId = bookingRequestDTO.getParkingSpotId();
        ParkingSpot parkingSpot = parkingSpotRepository.findById(parkingSpotId).orElseThrow(() ->
                new ResourceDoesNotExistException(parkingSpotId, "ParkingSpot"));
        if(!bookingRequestDTO.getStartTime().equals(booking.getStartTime()) || !bookingRequestDTO.getEndTime().equals(booking.getEndTime()))
            checkRequestedInterval(bookingRequestDTO, parkingSpot);
        configureBooking(booking, parkingSpot, bookingRequestDTO, booking.getStatus());
        return BookingResponseDTO.from(bookingRepository.save(booking));
    }


    public void removeBookingById(Long id) {
        if(!bookingRepository.existsById(id))
            throw new ResourceDoesNotExistException(id, "Booking");
        bookingRepository.deleteById(id);
    }

    // utility methods
    private void configureBooking(Booking booking, ParkingSpot parkingSpot, BookingRequestDTO bookingRequestDTO, BookingStatus bookingStatus) {
        booking.setParkingSpot(parkingSpot);
        booking.setStartTime(bookingRequestDTO.getStartTime());
        booking.setEndTime(bookingRequestDTO.getEndTime());
        booking.setUser(userRepository.findById(bookingRequestDTO.getUserId()).orElseThrow(() -> new ResourceDoesNotExistException(bookingRequestDTO.getUserId(), "User")));
        BookingStatus status = bookingRequestDTO.getStatus();
        booking.setStatus(Objects.requireNonNullElse(status, bookingStatus));
    }


    private void checkRequestedInterval(BookingRequestDTO bookingRequestDTO, ParkingSpot parkingSpot) {
        if(!bookingRequestDTO.getStartTime().isBefore(bookingRequestDTO.getEndTime()))
            throw new InvalidArgumentException("The start time of the booking cannot be later than the end time.");
        List<Booking> bookings = bookingRepository.findByParkingSpot(parkingSpot);
        for (Booking b : bookings) {
            if(doIntervalsOverlap(b.getStartTime(), b.getEndTime(), bookingRequestDTO.getStartTime(), bookingRequestDTO.getEndTime()))
                throw new InvalidArgumentException("The parking spot with id [%s] is not available for the selected period".formatted(parkingSpot.getId()));
        }

    }

    private boolean doIntervalsOverlap(Instant s1, Instant e1, Instant s2,  Instant e2) {
        return !s1.isBefore(s2) && !s1.isAfter(e2)
                || !e1.isBefore(s2) && !e1.isAfter(e2);
    }

}
