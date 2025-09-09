package com.example.parkingservice.service;

import com.example.parkingservice.dto.UserRequestDTO;
import com.example.parkingservice.dto.UserResponseDTO;
import com.example.parkingservice.exception.ResourceAlreadyExistsException;
import com.example.parkingservice.persistance.entity.User;
import com.example.parkingservice.persistance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        User user = userRepository.findByEmail(userRequestDTO.getEmail());
        if (user != null) {
            throw new ResourceAlreadyExistsException("User with email '%s' already exists".formatted(userRequestDTO.getEmail()));
        }
        user = new User();
        user.setEmail(userRequestDTO.getEmail());
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setPassword(userRequestDTO.getPassword());
        user.setMiddleName(userRequestDTO.getMiddleName());
        return UserResponseDTO.from(userRepository.save(user));
    }
}
