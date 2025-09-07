package com.example.parkingservice.dto;

import com.example.parkingservice.enums.BookingStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BookingRequestDTO {

    private Instant startTime;

    private Instant endTime;

    private Long userId;

    private Long parkingSpotId;

    private BookingStatus status;
}
