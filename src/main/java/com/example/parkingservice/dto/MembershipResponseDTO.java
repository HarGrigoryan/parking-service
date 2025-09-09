package com.example.parkingservice.dto;

import com.example.parkingservice.enums.MembershipStatus;
import com.example.parkingservice.persistance.entity.Membership;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class MembershipResponseDTO {

    private Long id;

    private Long userId;

    private Long residentialCommunityId;

    private MembershipStatus status;

    private Instant validFrom;

    private Instant validTo;

    private Instant createdAt;

    private Instant updatedAt;

    public static MembershipResponseDTO from(Membership membership) {
        if(membership == null)
            return null;
        MembershipResponseDTO membershipResponseDTO = new MembershipResponseDTO();
        membershipResponseDTO.setId(membership.getId());
        membershipResponseDTO.setUserId(membership.getUser().getId());
        membershipResponseDTO.setCreatedAt(membership.getCreatedAt());
        membershipResponseDTO.setUpdatedAt(membership.getUpdatedAt());
        membershipResponseDTO.setResidentialCommunityId(membership.getResidentialCommunity().getId());
        membershipResponseDTO.setStatus(membership.getStatus());
        membershipResponseDTO.setValidFrom(membership.getValidFrom());
        membershipResponseDTO.setValidTo(membership.getValidTo());
        return membershipResponseDTO;
    }
}
