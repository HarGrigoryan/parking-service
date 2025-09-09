package com.example.parkingservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDTO {

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private String password;

}
