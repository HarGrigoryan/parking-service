package com.example.parkingservice.dto;

import com.example.parkingservice.enums.BookingStatus;
import com.example.parkingservice.persistance.entity.Booking;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
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
}
