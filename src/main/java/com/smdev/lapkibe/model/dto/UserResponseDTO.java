package com.smdev.lapkibe.model.dto;

import com.smdev.lapkibe.model.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponseDTO {
    private String email;
    private String fullName;
    private UserRole role;
}
