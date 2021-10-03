package com.smdev.lapkibe.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class SendMailDTO {
    @Size(min = 2, max = 40, message = "Invalid full name")
    private String fullName;
    @Pattern(regexp = "(^\\+[0-9]{8,15}$)", message = "Invalid number")
    private String number;
    @Email(message = "Invalid email")
    private String email;
    @Pattern(regexp = "((\\w|\\s|\\.|[_?!.\"'()]){1,300})", message = "Password doesn't match requirements")
    private String message;
}
