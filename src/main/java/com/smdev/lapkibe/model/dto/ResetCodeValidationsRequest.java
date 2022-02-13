package com.smdev.lapkibe.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class ResetCodeValidationsRequest {
    private String code;
    @Email
    private String email;
    @Pattern(regexp = "((\\w|\\s|\\.|_){6,30})", message = "Password doesn't match requirements")
    private String newPassword;
}
