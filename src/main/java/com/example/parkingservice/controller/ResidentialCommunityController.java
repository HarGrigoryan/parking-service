package com.example.parkingservice.controller;

import com.example.parkingservice.dto.ResidentialCommunityRequestDTO;
import com.example.parkingservice.dto.ResidentialCommunityResponseDTO;
import com.example.parkingservice.service.ResidentialCommunityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/residential-communities")
@RequiredArgsConstructor
public class ResidentialCommunityController {

    private final ResidentialCommunityService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResidentialCommunityResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ResidentialCommunityResponseDTO> create(@RequestBody @Valid ResidentialCommunityRequestDTO residentialCommunityRequestDTO) {
        return ResponseEntity.ok(service.create(residentialCommunityRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResidentialCommunityResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ResidentialCommunityRequestDTO residentialCommunityRequestDTO) {
        return ResponseEntity.ok(service.update(id, residentialCommunityRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
