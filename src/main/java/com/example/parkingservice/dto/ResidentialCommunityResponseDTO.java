package com.example.parkingservice.dto;


import com.example.parkingservice.persistance.entity.ResidentialCommunity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResidentialCommunityResponseDTO {

    private Long id;

    private String name;

    private String country;

    private String city;

    private String street;

    private String zipcode;

    private long createdAt;

    private long updatedAt;


    public static ResidentialCommunityResponseDTO entityToDTO(ResidentialCommunity rC)
    {
        ResidentialCommunityResponseDTO dto = new ResidentialCommunityResponseDTO();
        dto.setId(rC.getId());
        dto.setName(rC.getName());
        dto.setCountry(rC.getCountry());
        dto.setCity(rC.getCity());
        dto.setStreet(rC.getStreet());
        dto.setZipcode(rC.getZipcode());
        dto.setCreatedAt(rC.getCreatedAt().toEpochMilli());
        dto.setUpdatedAt(rC.getUpdatedAt().toEpochMilli());
        return dto;
    }
}
