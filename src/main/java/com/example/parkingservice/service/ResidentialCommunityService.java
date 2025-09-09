package com.example.parkingservice.service;

import com.example.parkingservice.dto.ResidentialCommunityRequestDTO;
import com.example.parkingservice.dto.ResidentialCommunityResponseDTO;
import com.example.parkingservice.exception.ResourceAlreadyExistsException;
import com.example.parkingservice.exception.ResourceDoesNotExistException;
import com.example.parkingservice.persistance.entity.ResidentialCommunity;
import com.example.parkingservice.persistance.repository.ResidentialCommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResidentialCommunityService {

    private final ResidentialCommunityRepository residentialCommunityRepository;

    public ResidentialCommunityResponseDTO create(ResidentialCommunityRequestDTO residentialCommunityRequestDTO) {
        ResidentialCommunity residentialCommunity = residentialCommunityRepository.findByName(residentialCommunityRequestDTO.getName());
        if(residentialCommunity != null)
            throw new ResourceAlreadyExistsException("Residential community already exists");
        residentialCommunity = new ResidentialCommunity();
        residentialCommunity.setCountry(residentialCommunityRequestDTO.getCountry());
        residentialCommunity.setCity(residentialCommunityRequestDTO.getCity());
        residentialCommunity.setStreet(residentialCommunityRequestDTO.getStreet());
        residentialCommunity.setZipcode(residentialCommunityRequestDTO.getZipcode());
        residentialCommunity.setName(residentialCommunityRequestDTO.getName());
        return ResidentialCommunityResponseDTO.from(residentialCommunityRepository.save(residentialCommunity));
    }

    public ResidentialCommunityResponseDTO getById(Long id) {
        return ResidentialCommunityResponseDTO.from(residentialCommunityRepository.findById(id).orElseThrow(() -> new ResourceDoesNotExistException(id, "Residential community")));
    }

    public ResidentialCommunityResponseDTO update(Long id, ResidentialCommunityRequestDTO residentialCommunityRequestDTO) {
        ResidentialCommunity residentialCommunity = residentialCommunityRepository.findById(id).orElseThrow(() -> new ResourceDoesNotExistException(id, "Residential community"));
        residentialCommunity.setName(residentialCommunityRequestDTO.getName());
        residentialCommunity.setCountry(residentialCommunityRequestDTO.getCountry());
        residentialCommunity.setCity(residentialCommunityRequestDTO.getCity());
        residentialCommunity.setStreet(residentialCommunityRequestDTO.getStreet());
        residentialCommunity.setZipcode(residentialCommunityRequestDTO.getZipcode());
        return ResidentialCommunityResponseDTO.from(residentialCommunityRepository.save(residentialCommunity));
    }

    public void deleteById(Long id) {
        ResidentialCommunity residentialCommunity = residentialCommunityRepository.findById(id).orElseThrow(() -> new ResourceDoesNotExistException(id, "Residential community"));
        residentialCommunityRepository.delete(residentialCommunity);
    }

}
