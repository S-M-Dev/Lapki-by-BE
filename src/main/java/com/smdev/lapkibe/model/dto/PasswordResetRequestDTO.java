package com.smdev.lapkibe.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
public class PasswordResetRequestDTO {
    @Email
    private String email;
}
