package com.smdev.lapkibe.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class UserRegistrationDTO {
    @NotBlank(message = "Full name cannot be blank")
    @Size(min = 2, max = 40, message = "Invalid full name")
    private String fullName;
    @Email(message = "Invalid email")
    private String email;
    @Pattern(regexp = "((\\w|\\s|\\.|_){6,30})", message = "Password doesn't match requirements")
    private String password;
}
