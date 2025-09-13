package com.example.parkingservice.criteria;

import com.example.parkingservice.enums.ParkingSpotStatus;
import com.example.parkingservice.enums.ParkingSpotType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.Instant;

@Setter
public class ParkingSpotSearchCriteria extends SearchCriteria{

    @Getter
    private String spotNumber;

    @Getter
    private ParkingSpotType type;

    private ParkingSpotStatus status;

    @Getter
    private Long residentialCommunityId;

    @Getter
    private Boolean onlyAvailable = false;

    @Getter
    private Instant startTime;

    @Getter
    private Instant endTime;

    @Getter
    private String sortBy = "id";

    @Getter
    private Instant bufferedStartTime;

    @Getter
    private Instant bufferedEndTime;

    @Override
    public PageRequest buildPageRequest() {
        PageRequest pageRequest = super.buildPageRequest();
        return pageRequest.withSort(Sort.by(sortBy).ascending());
    }

    public ParkingSpotStatus getStatus() {
        if(onlyAvailable)
            return ParkingSpotStatus.AVAILABLE;
        return status;
    }

}
