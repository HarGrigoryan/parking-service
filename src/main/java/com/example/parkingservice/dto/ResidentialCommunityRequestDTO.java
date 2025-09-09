package com.example.parkingservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResidentialCommunityRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    private String zipcode;
}
