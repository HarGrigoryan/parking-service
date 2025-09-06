package com.example.parkingservice.dto;

import com.example.parkingservice.enums.ParkingSpotStatus;
import com.example.parkingservice.enums.ParkingSpotType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingSpotRequestDTO {

    @NotBlank(message = "The spot must have a designated spot number, unique within the community.")
    private String spotNumber;

    private ParkingSpotType type;

    private ParkingSpotStatus status;

    private Long residentialCommunityId;

}
