package com.example.parkingservice.criteria;

import com.example.parkingservice.enums.ParkingSpotStatus;
import com.example.parkingservice.enums.ParkingSpotType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.Instant;

@Setter
@Getter
public class ParkingSpotSearchCriteria extends SearchCriteria{

    private String spotNumber;

    private ParkingSpotType type;

    private ParkingSpotStatus status;

    private Long residentialCommunityId;

    private Boolean onlyAvailable;

    private Instant startTime;

    private Instant endTime;

    private String sortBy = "id";

    private Instant bufferedStartTime;

    private Instant bufferedEndTime;

    @Override
    public PageRequest buildPageRequest() {
        PageRequest pageRequest = super.buildPageRequest();
        return pageRequest.withSort(Sort.by(sortBy).ascending());
    }

}
