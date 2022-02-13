package com.smdev.lapkibe.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smdev.lapkibe.model.dto.PasswordChangeDTO;
import com.smdev.lapkibe.model.dto.UserLoginDTO;
import com.smdev.lapkibe.model.dto.UserRegistrationDTO;
import com.smdev.lapkibe.model.dto.UserResponseDTO;
import com.smdev.lapkibe.model.dto.UserUpdateRequest;
import com.smdev.lapkibe.model.entity.UserEntity;

@Service
public interface UserService {
    String registerUser(UserRegistrationDTO userRegistrationDTO);
    String loginUser(UserLoginDTO userLoginDTO);
    void authenticate(UserDetails userDetails);
    String changePassword(PasswordChangeDTO passwordChangeDTO);
    Optional<UserEntity> getUserByEmail(String email);
    UserResponseDTO getCurrent();
    UserResponseDTO updateUser(final UserUpdateRequest userUpdateRequest);
    void updateImage(final MultipartFile file);
    byte[] getImage();
}
