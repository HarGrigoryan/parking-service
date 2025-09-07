package com.example.parkingservice.criteria;

import com.example.parkingservice.enums.BookingStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Setter
@Getter
public class BookingSearchCriteria extends SearchCriteria {

    private Instant startTime = LocalDateTime.of(1000, 1, 1, 0, 0).toInstant(ZoneOffset.MIN);

    private Instant endTime = LocalDateTime.of(9999, 1, 1, 0, 0).toInstant(ZoneOffset.MAX);

    private Long userId;

    private Long parkingSpotId;

    private BookingStatus status;

    private Instant createdAt = LocalDateTime.of(1000, 1, 1, 0, 0).toInstant(ZoneOffset.MIN);

    private Instant updatedAt =  LocalDateTime.of(1000, 1, 1, 0, 0).toInstant(ZoneOffset.MIN);

    private String sortBy = "createdAt";

    @Override
    public PageRequest buildPageRequest() {
        PageRequest pageRequest = super.buildPageRequest();

        return pageRequest.withSort(
                Sort.by(sortBy).descending()
        );
    }

}
