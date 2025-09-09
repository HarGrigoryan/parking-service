package com.example.parkingservice.controller;

import com.example.parkingservice.dto.MembershipCreateRequestDTO;
import com.example.parkingservice.dto.MembershipResponseDTO;
import com.example.parkingservice.dto.MembershipUpdateRequestDTO;
import com.example.parkingservice.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/memberships")
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    @GetMapping("/{id}")
    public ResponseEntity<MembershipResponseDTO> getMembershipById(@PathVariable Long id) {
        return ResponseEntity.ok(membershipService.getMembershipById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MembershipResponseDTO> updateMembership(@PathVariable Long id, @RequestBody MembershipUpdateRequestDTO membershipUpdateRequestDTO) {
        return ResponseEntity.ok(membershipService.updateMembership(id, membershipUpdateRequestDTO));
    }

    @PostMapping
    public ResponseEntity<MembershipResponseDTO> createMembership(@RequestBody MembershipCreateRequestDTO membershipCreateRequestDTO) {
        return ResponseEntity.ok(membershipService.createMembership(membershipCreateRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteMembership(@PathVariable Long id) {
        membershipService.softDeleteMembership(id);
        return ResponseEntity.noContent().build();
    }

}
