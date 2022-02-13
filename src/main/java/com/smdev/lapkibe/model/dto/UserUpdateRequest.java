package com.smdev.lapkibe.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserUpdateRequest {
    @Size(min = 2, max = 40, message = "Invalid full name")
    private String fullName;
    @Email(message = "Invalid email")
    private String email;
    @Pattern(regexp = "\\+[0-9]{7,12}", message = "Invalid phone format")
    private String phoneNumber;
    @Pattern(regexp = "((\\w|\\s|\\.|_){6,30})", message = "Password doesn't match requirements")
    private String password;
}
