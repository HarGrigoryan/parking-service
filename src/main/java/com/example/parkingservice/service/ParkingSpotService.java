package com.example.parkingservice.service;

import com.example.parkingservice.criteria.ParkingSpotSearchCriteria;
import com.example.parkingservice.dto.PageResponseDTO;
import com.example.parkingservice.dto.ParkingSpotRequestDTO;
import com.example.parkingservice.dto.ParkingSpotResponseDTO;
import com.example.parkingservice.exception.InvalidArgumentException;
import com.example.parkingservice.exception.ResourceAlreadyExistsException;
import com.example.parkingservice.exception.ResourceDoesNotExistException;
import com.example.parkingservice.persistance.entity.ParkingSpot;
import com.example.parkingservice.persistance.entity.ResidentialCommunity;
import com.example.parkingservice.persistance.repository.ParkingSpotRepository;
import com.example.parkingservice.persistance.repository.ResidentialCommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

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
        parkingSpotRepository.delete(parkingSpot);
    }

    public PageResponseDTO<ParkingSpotResponseDTO> getParkingSpots(ParkingSpotSearchCriteria searchCriteria) {
        //setting default values
        if(searchCriteria.getStartTime() == null)
            searchCriteria.setStartTime(Instant.now());
        if(searchCriteria.getEndTime() == null)
            searchCriteria.setEndTime(searchCriteria.getStartTime().plus(1, ChronoUnit.HOURS));
        searchCriteria.setBufferedStartTime(searchCriteria.getStartTime().minus(ParkingSpotSearchCriteria.BUFFER));
        searchCriteria.setBufferedEndTime(searchCriteria.getEndTime().plus(ParkingSpotSearchCriteria.BUFFER));
        if(!searchCriteria.getStartTime().isBefore(searchCriteria.getEndTime()))
            throw new InvalidArgumentException("End time cannot be after start time.");
        Page<ParkingSpotResponseDTO> page = parkingSpotRepository.findAll(searchCriteria, searchCriteria.buildPageRequest());
        return PageResponseDTO.from(page);
    }
}
