package com.example.parkingservice.controller;

import com.example.parkingservice.dto.ResidentialCommunityCreateDTO;
import com.example.parkingservice.dto.ResidentialCommunityResponseDTO;
import com.example.parkingservice.service.ResidentialCommunityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/residential-communities")
@RequiredArgsConstructor
public class ResidentialCommunityController {

    private final ResidentialCommunityService service;

    @PostMapping
    public ResponseEntity<ResidentialCommunityResponseDTO> create(@RequestBody @Valid    ResidentialCommunityCreateDTO residentialCommunityCreateDTO) {
        return ResponseEntity.ok(service.create(residentialCommunityCreateDTO));
    }

}
