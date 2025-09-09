package com.example.parkingservice.service;

import com.example.parkingservice.dto.MembershipCreateRequestDTO;
import com.example.parkingservice.dto.MembershipResponseDTO;
import com.example.parkingservice.dto.MembershipUpdateRequestDTO;
import com.example.parkingservice.enums.MembershipStatus;
import com.example.parkingservice.exception.ResourceAlreadyExistsException;
import com.example.parkingservice.exception.ResourceDoesNotExistException;
import com.example.parkingservice.persistance.entity.Membership;
import com.example.parkingservice.persistance.entity.ResidentialCommunity;
import com.example.parkingservice.persistance.entity.User;
import com.example.parkingservice.persistance.repository.MembershipRepository;
import com.example.parkingservice.persistance.repository.ResidentialCommunityRepository;
import com.example.parkingservice.persistance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final ResidentialCommunityRepository residentialCommunityRepository;

    public MembershipResponseDTO getMembershipById(Long id) {
        return MembershipResponseDTO.from(membershipRepository.findById(id).
                orElseThrow(() -> new ResourceDoesNotExistException(id, "Membership")));
    }

    public MembershipResponseDTO createMembership(MembershipCreateRequestDTO membershipCreateRequestDTO) {
        Long userId = membershipCreateRequestDTO.getUserId();
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceDoesNotExistException(userId, "User"));
        Long residentialCommunityId = membershipCreateRequestDTO.getResidentialCommunityId();
        ResidentialCommunity residentialCommunity = residentialCommunityRepository.findById(residentialCommunityId).
                orElseThrow(() -> new ResourceDoesNotExistException(residentialCommunityId, "ResidentialCommunity"));
        Membership membership = membershipRepository.findByUserAndResidentialCommunity(user, residentialCommunity);
        if(membership != null) {
            throw new ResourceAlreadyExistsException("The user with id [%s] is already a member of the residential community with id [%s].".formatted(userId, residentialCommunityId));
        }
        membership = new Membership();
        membership.setUser(user);
        membership.setResidentialCommunity(residentialCommunity);
        membership.setStatus(membershipCreateRequestDTO.getStatus());
        membership.setValidFrom(membershipCreateRequestDTO.getValidFrom());
        membership.setValidTo(membershipCreateRequestDTO.getValidTo());
        return MembershipResponseDTO.from(membershipRepository.save(membership));
    }

    public MembershipResponseDTO updateMembership(Long membershipId, MembershipUpdateRequestDTO membershipUpdateRequestDTO) {
        Membership membership = membershipRepository.findById(membershipId).orElseThrow(() -> new ResourceDoesNotExistException(membershipId, "Membership"));
        membership.setStatus(membershipUpdateRequestDTO.getStatus());
        membership.setValidFrom(membershipUpdateRequestDTO.getValidFrom());
        membership.setValidTo(membershipUpdateRequestDTO.getValidTo());
        return MembershipResponseDTO.from(membershipRepository.save(membership));
    }

    public void softDeleteMembership(Long membershipId) {
        Membership membership = membershipRepository.findById(membershipId).orElseThrow(() -> new ResourceDoesNotExistException(membershipId, "Membership"));
        membership.setStatus(MembershipStatus.CANCELLED);
        membershipRepository.save(membership);
    }
}
