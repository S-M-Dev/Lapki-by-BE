package com.smdev.lapkibe.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Data
public class UserLoginDTO {
    @Email(message = "Invalid email")
    private String email;
    @Pattern(regexp = "((\\w|\\s|\\.|_){6,30})", message = "Password doesn't match requirements")
    private String password;
}
