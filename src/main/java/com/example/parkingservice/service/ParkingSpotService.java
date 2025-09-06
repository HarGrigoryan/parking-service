package com.example.parkingservice.service;

import com.example.parkingservice.dto.ParkingSpotRequestDTO;
import com.example.parkingservice.dto.ParkingSpotResponseDTO;
import com.example.parkingservice.exception.ResourceAlreadyExistsException;
import com.example.parkingservice.exception.ResourceDoesNotExistException;
import com.example.parkingservice.persistance.entity.ParkingSpot;
import com.example.parkingservice.persistance.entity.ResidentialCommunity;
import com.example.parkingservice.persistance.repository.ParkingSpotRepository;
import com.example.parkingservice.persistance.repository.ResidentialCommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;
    private final ResidentialCommunityRepository residentialCommunityRepository;

    public ParkingSpotResponseDTO getById(Long id) {
        ParkingSpot parkingSpot = parkingSpotRepository.findById(id).orElse(null);
        if(parkingSpot == null)
            throw new ResourceDoesNotExistException(id, "Parking spot");
        return ParkingSpotResponseDTO.fromParkingSpot(parkingSpot);
    }

    public ParkingSpotResponseDTO createParkingSpot(ParkingSpotRequestDTO parkingSpotRequestDTO) {
        ParkingSpot parkingSpot = parkingSpotRepository.findBySpotNumberAndResidentialCommunityId(parkingSpotRequestDTO.getSpotNumber(), parkingSpotRequestDTO.getResidentialCommunityId());
        if(parkingSpot != null)
            throw new ResourceAlreadyExistsException("Parking Spot already exists");
        parkingSpot = new ParkingSpot();
        parkingSpot.setSpotNumber(parkingSpotRequestDTO.getSpotNumber());
        parkingSpot.setType(parkingSpotRequestDTO.getType());
        parkingSpot.setStatus(parkingSpotRequestDTO.getStatus());
        ResidentialCommunity residentialCommunity = residentialCommunityRepository.findById(parkingSpotRequestDTO.getResidentialCommunityId()).orElse(null);
        if(residentialCommunity == null)
            throw new ResourceDoesNotExistException(parkingSpotRequestDTO.getResidentialCommunityId(), "Residential Community");
        parkingSpot.setResidentialCommunity(residentialCommunity);
        return ParkingSpotResponseDTO.fromParkingSpot(parkingSpotRepository.save(parkingSpot));

    }

    public ParkingSpotResponseDTO updateParkingSpot(Long id, ParkingSpotRequestDTO parkingSpotRequestDTO) {

        ParkingSpot parkingSpot = parkingSpotRepository.findById(id).orElse(null);
        if(parkingSpot == null)
            throw new ResourceDoesNotExistException(id, "Parking spot");
        String spotNumber = parkingSpotRequestDTO.getSpotNumber();
        Long residentialCommunityId = parkingSpotRequestDTO.getResidentialCommunityId();
        if(!spotNumber.equals(parkingSpot.getSpotNumber()) || !residentialCommunityId.equals(parkingSpot.getResidentialCommunity().getId()))
        {
            ParkingSpot check = parkingSpotRepository.findBySpotNumberAndResidentialCommunityId(spotNumber, residentialCommunityId);
            if(check != null)
                throw new ResourceAlreadyExistsException("Parking Spot with spot number" +
                        " [%s] and community id [%s] already exists".formatted(spotNumber, residentialCommunityId));
        }
        parkingSpot.setSpotNumber(spotNumber);
        parkingSpot.setResidentialCommunity(residentialCommunityRepository.findById(residentialCommunityId).orElseThrow(() -> new ResourceDoesNotExistException(residentialCommunityId, "Residential Community")));
        parkingSpot.setType(parkingSpotRequestDTO.getType());
        parkingSpot.setStatus(parkingSpotRequestDTO.getStatus());
        return ParkingSpotResponseDTO.fromParkingSpot(parkingSpotRepository.save(parkingSpot));
    }

    public void deleteParkingSpotById(Long id) {
        ParkingSpot parkingSpot = parkingSpotRepository.findById(id).orElseThrow(() -> new ResourceDoesNotExistException(id, "Parking spot"));
        parkingSpotRepository.deleteById(parkingSpot.getId());
    }

}
