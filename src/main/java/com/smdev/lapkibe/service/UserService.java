package com.smdev.lapkibe.service;

import com.smdev.lapkibe.model.dto.UserLoginDTO;
import com.smdev.lapkibe.model.dto.UserRegistrationDTO;
import com.smdev.lapkibe.model.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    String registerUser(UserRegistrationDTO userRegistrationDTO);
    String loginUser(UserLoginDTO userLoginDTO);
    void authenticate(UserDetails userDetails);
    Optional<UserEntity> getUserByEmail(String email);
}
