package com.smdev.lapkibe.service;

import com.smdev.lapkibe.model.dto.UserLoginDTO;
import com.smdev.lapkibe.model.dto.UserRegistrationDTO;
import com.smdev.lapkibe.model.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    boolean registerUser(UserRegistrationDTO userRegistrationDTO);
    boolean loginUser(UserLoginDTO userLoginDTO);
    void authenticate(String email);
    Optional<UserEntity> getUserByEmail(String email);
}
