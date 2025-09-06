package com.example.parkingservice.controller;

import com.example.parkingservice.dto.ParkingSpotRequestDTO;
import com.example.parkingservice.dto.ParkingSpotResponseDTO;
import com.example.parkingservice.service.ParkingSpotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/parking-spots")
@RequiredArgsConstructor
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpotResponseDTO> getParkingSpotById(@PathVariable Long id) {
        return ResponseEntity.ok(parkingSpotService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ParkingSpotResponseDTO> createParkingSpot(@RequestBody @Valid ParkingSpotRequestDTO parkingSpotRequestDTO) {
        return ResponseEntity.ok(parkingSpotService.createParkingSpot(parkingSpotRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpotResponseDTO> updateParkingSpotById(@PathVariable Long id, @Valid @RequestBody ParkingSpotRequestDTO parkingSpotRequestDTO) {
        return ResponseEntity.ok(parkingSpotService.updateParkingSpot(id, parkingSpotRequestDTO));
    }

    // deletes all bookings affiliated with this spot
    @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteParkingSpotById(@PathVariable Long id) {
        parkingSpotService.deleteParkingSpotById(id);
        return ResponseEntity.noContent().build();
    }

}
