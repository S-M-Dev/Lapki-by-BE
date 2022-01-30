package com.smdev.lapkibe.mapper;

import com.smdev.lapkibe.model.dto.UserRegistrationDTO;
import com.smdev.lapkibe.model.dto.UserResponseDTO;
import com.smdev.lapkibe.model.entity.UserEntity;
import com.smdev.lapkibe.model.entity.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity registrationToEntity(UserRegistrationDTO userRegistrationDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userRegistrationDTO.getEmail());
        userEntity.setFullName(userRegistrationDTO.getFullName());
        userEntity.setPassword(userRegistrationDTO.getPassword());
        userEntity.setPhoneNumber("");
        userEntity.setRole(UserRole.ROLE_USER);
        return userEntity;
    }

    public UserResponseDTO toResponse(UserEntity userEntity){
        return UserResponseDTO.builder()
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .fullName(userEntity.getFullName())
                .build();
    }

}
