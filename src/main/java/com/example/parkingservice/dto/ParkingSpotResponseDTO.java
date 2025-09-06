package com.example.parkingservice.dto;

import com.example.parkingservice.enums.ParkingSpotStatus;
import com.example.parkingservice.enums.ParkingSpotType;
import com.example.parkingservice.persistance.entity.ParkingSpot;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingSpotResponseDTO {

    private Long id;

    private String spotNumber;

    private ParkingSpotType type;

    private ParkingSpotStatus status;

    private Long residentialCommunityId;

    public static ParkingSpotResponseDTO fromParkingSpot(ParkingSpot parkingSpot) {
        ParkingSpotResponseDTO parkingSpotResponseDTO = new ParkingSpotResponseDTO();
        parkingSpotResponseDTO.setId(parkingSpot.getId());
        parkingSpotResponseDTO.setStatus(parkingSpot.getStatus());
        parkingSpotResponseDTO.setSpotNumber(parkingSpot.getSpotNumber());
        parkingSpotResponseDTO.setResidentialCommunityId(parkingSpot.getResidentialCommunity().getId());
        parkingSpotResponseDTO.setType(parkingSpot.getType());
        return parkingSpotResponseDTO;
    }
}
