package com.example.parkingservice.dto;

import com.example.parkingservice.enums.BookingStatus;
import com.example.parkingservice.persistance.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {

    private Long id;

    private Instant startTime;

    private Instant endTime;

    private Long userId;

    private Long parkingSpotId;

    private Instant createdAt;

    private Instant updatedAt;

    private BookingStatus status;

    public static BookingResponseDTO from(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.id = booking.getId();
        dto.startTime = booking.getStartTime();
        dto.endTime = booking.getEndTime();
        dto.userId = booking.getUser().getId();
        dto.parkingSpotId = booking.getParkingSpot().getId();
        dto.createdAt = booking.getCreatedAt();
        dto.updatedAt = booking.getUpdatedAt();
        dto.status = booking.getStatus();
        return dto;
    }

    public static List<BookingResponseDTO> from(List<Booking> bookings) {
        List<BookingResponseDTO> dtos = new ArrayList<>();
        for(Booking booking : bookings) {
            dtos.add(from(booking));
        }
        return dtos;
    }
}
