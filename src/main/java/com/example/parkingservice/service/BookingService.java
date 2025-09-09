package com.example.parkingservice.service;

import com.example.parkingservice.criteria.BookingSearchCriteria;
import com.example.parkingservice.dto.BookingRequestDTO;
import com.example.parkingservice.dto.BookingResponseDTO;
import com.example.parkingservice.dto.PageResponseDTO;
import com.example.parkingservice.enums.BookingRecurrence;
import com.example.parkingservice.enums.BookingStatus;
import com.example.parkingservice.exception.BookingException;
import com.example.parkingservice.exception.InvalidArgumentException;
import com.example.parkingservice.exception.ResourceDoesNotExistException;
import com.example.parkingservice.persistance.entity.Booking;
import com.example.parkingservice.persistance.entity.ParkingSpot;
import com.example.parkingservice.persistance.repository.BookingRepository;
import com.example.parkingservice.persistance.repository.MembershipRepository;
import com.example.parkingservice.persistance.repository.ParkingSpotRepository;
import com.example.parkingservice.persistance.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ParkingSpotRepository parkingSpotRepository;
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;

    @Value("${booking.interval.buffer}")
    private Integer buffer;

    public Duration BUFFER ;

    @PostConstruct
    public void init() {
        BUFFER = Duration.ofMinutes(buffer);
    }

    public BookingResponseDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceDoesNotExistException(id, "Booking"));
        return  BookingResponseDTO.from(booking);
    }


    public List<BookingResponseDTO> createBooking(BookingRequestDTO bookingRequestDTO) {
        Long parkingSpotId = bookingRequestDTO.getParkingSpotId();
        ParkingSpot parkingSpot = parkingSpotRepository.findById(parkingSpotId).orElseThrow(() ->
                new ResourceDoesNotExistException(parkingSpotId, "ParkingSpot"));
        Long residentialCommunityId = parkingSpot.getResidentialCommunity().getId();
        membershipRepository.findActiveByUserIdAndResidentialCommunityId(bookingRequestDTO.getUserId(), residentialCommunityId)
                .orElseThrow(() -> new BookingException("The user with id [%s]  is not part of the residential community with id [%s].".formatted(bookingRequestDTO.getUserId(), residentialCommunityId)));
        BookingRecurrence recurrence = bookingRequestDTO.getRecurrence();
        int repetitions = recurrence == BookingRecurrence.NONE ? 1 : bookingRequestDTO.getRepetitions();
        List<Booking> bookingsToSave = new ArrayList<>();
        for(int i = 0; i < repetitions; i++) {
            int dayCount = recurrence.getDayCount();
            Instant startPlus = bookingRequestDTO.getStartTime().plus((long) i * dayCount, ChronoUnit.DAYS );
            Instant endPlus = bookingRequestDTO.getEndTime().plus((long) i * dayCount, ChronoUnit.DAYS);
            checkRequestedInterval(startPlus, endPlus, parkingSpot);
            Booking booking = new Booking();
            configureBooking(booking, parkingSpot,
                   startPlus, endPlus, bookingRequestDTO, BookingStatus.BOOKED);
            bookingsToSave.add(booking);
        }
        return BookingResponseDTO.from(bookingRepository.saveAll(bookingsToSave));
    }

    public BookingResponseDTO updateBooking(BookingRequestDTO bookingRequestDTO, Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceDoesNotExistException(id, "Booking"));
        Long parkingSpotId = bookingRequestDTO.getParkingSpotId();
        ParkingSpot parkingSpot = parkingSpotRepository.findById(parkingSpotId).orElseThrow(() ->
                new ResourceDoesNotExistException(parkingSpotId, "ParkingSpot"));
        if(!bookingRequestDTO.getStartTime().equals(booking.getStartTime()) || !bookingRequestDTO.getEndTime().equals(booking.getEndTime()))
            checkRequestedInterval(bookingRequestDTO.getStartTime(), bookingRequestDTO.getEndTime(), parkingSpot);
        configureBooking(booking, parkingSpot, bookingRequestDTO.getStartTime(), bookingRequestDTO.getEndTime(), bookingRequestDTO, booking.getStatus());
        return BookingResponseDTO.from(bookingRepository.save(booking));
    }


    public void softDeleteBookingById(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceDoesNotExistException(id, "Booking"));
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    // utility methods
    private void configureBooking(Booking booking, ParkingSpot parkingSpot,Instant startTime, Instant endTime, BookingRequestDTO bookingRequestDTO, BookingStatus bookingStatus) {
        booking.setParkingSpot(parkingSpot);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setUser(userRepository.findById(bookingRequestDTO.getUserId()).orElseThrow(() -> new ResourceDoesNotExistException(bookingRequestDTO.getUserId(), "User")));
        BookingStatus status = bookingRequestDTO.getStatus();
        booking.setStatus(Objects.requireNonNullElse(status, bookingStatus));
    }


    private void checkRequestedInterval(Instant startTime, Instant endTime, ParkingSpot parkingSpot) {
        if(startTime.isBefore(Instant.now().minus(5, ChronoUnit.MINUTES)))
            throw new InvalidArgumentException("The start time of the booking cannot be later than the end time.");
        if(!startTime.isBefore(endTime))
            throw new InvalidArgumentException("The start time of the booking cannot be later than the end time.");
        List<Booking> bookings = bookingRepository.findByParkingSpot(parkingSpot);
        for (Booking b : bookings) {
            if(doIntervalsOverlap(b.getStartTime(), b.getEndTime(), startTime.minus(BUFFER), endTime.plus(BUFFER)))
                throw new InvalidArgumentException("The parking spot with id [%s] is not available for the selected period".formatted(parkingSpot.getId()));
        }

    }

    private boolean doIntervalsOverlap(Instant s1, Instant e1, Instant s2,  Instant e2) {
        return !s1.isAfter(e2) && !e1.isBefore(s2);
    }

    public PageResponseDTO<BookingResponseDTO> getBookings(BookingSearchCriteria criteria) {
        Page<BookingResponseDTO> page = bookingRepository.findAll(criteria, criteria.buildPageRequest());
        return PageResponseDTO.from(page);
    }
}
