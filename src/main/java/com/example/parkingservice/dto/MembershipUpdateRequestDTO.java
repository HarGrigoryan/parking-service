package com.example.parkingservice.dto;

import com.example.parkingservice.enums.MembershipStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class MembershipUpdateRequestDTO {

    private MembershipStatus status;

    private Instant validFrom;

    private Instant validTo;

}
