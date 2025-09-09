package com.example.parkingservice.controller;

import com.example.parkingservice.criteria.BookingSearchCriteria;
import com.example.parkingservice.dto.BookingRequestDTO;
import com.example.parkingservice.dto.BookingResponseDTO;
import com.example.parkingservice.dto.PageResponseDTO;
import com.example.parkingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping
    public PageResponseDTO<BookingResponseDTO> getBookings(BookingSearchCriteria criteria) {
        return bookingService.getBookings(criteria);
    }

    @PostMapping
    public ResponseEntity<List<BookingResponseDTO>> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
        return ResponseEntity.ok(bookingService.createBooking(bookingRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> updateBooking(@PathVariable Long id, @RequestBody BookingRequestDTO bookingRequestDTO) {
        return ResponseEntity.ok(bookingService.updateBooking(bookingRequestDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteBookingById(@PathVariable Long id) {
        bookingService.softDeleteBookingById(id);
        return ResponseEntity.noContent().build();
    }
}
