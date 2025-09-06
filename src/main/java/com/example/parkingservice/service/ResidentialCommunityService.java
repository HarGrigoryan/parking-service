package com.example.parkingservice.service;

import com.example.parkingservice.dto.ResidentialCommunityCreateDTO;
import com.example.parkingservice.dto.ResidentialCommunityResponseDTO;
import com.example.parkingservice.exception.ResourceAlreadyExistsException;
import com.example.parkingservice.persistance.entity.ResidentialCommunity;
import com.example.parkingservice.persistance.repository.ResidentialCommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResidentialCommunityService {

    private final ResidentialCommunityRepository residentialCommunityRepository;

    public ResidentialCommunityResponseDTO create(ResidentialCommunityCreateDTO residentialCommunityCreateDTO) {
        ResidentialCommunity residentialCommunity = residentialCommunityRepository.findByName(residentialCommunityCreateDTO.getName());
        if(residentialCommunity != null)
            throw new ResourceAlreadyExistsException("Residential community already exists");
        residentialCommunity = new ResidentialCommunity();
        residentialCommunity.setCountry(residentialCommunityCreateDTO.getCountry());
        residentialCommunity.setCity(residentialCommunityCreateDTO.getCity());
        residentialCommunity.setStreet(residentialCommunityCreateDTO.getStreet());
        residentialCommunity.setZipcode(residentialCommunityCreateDTO.getZipcode());
        residentialCommunity.setName(residentialCommunityCreateDTO.getName());
        return ResidentialCommunityResponseDTO.entityToDTO(residentialCommunityRepository.save(residentialCommunity));
    }
}
