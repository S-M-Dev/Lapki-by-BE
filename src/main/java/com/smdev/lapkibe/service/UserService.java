package com.smdev.lapkibe.service;

import com.smdev.lapkibe.model.dto.UserRegistrationDTO;
import com.smdev.lapkibe.model.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    boolean registerUser(UserRegistrationDTO userRegistrationDTO);
    Optional<UserEntity> getUserByEmail(String email);
}
