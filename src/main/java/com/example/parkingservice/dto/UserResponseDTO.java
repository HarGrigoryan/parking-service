package com.example.parkingservice.dto;

import com.example.parkingservice.persistance.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private Boolean enabled;

    public static UserResponseDTO from(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setMiddleName(user.getMiddleName());
        userResponseDTO.setLastName(user.getLastName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setEnabled(user.getEnabled());
        return userResponseDTO;
    }

}
