package com.smdev.lapkibe.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class PasswordChangeDTO {
    @Pattern(regexp = "((\\w|\\s|\\.|_){6,30})", message = "Password doesn't match requirements")
    private String password;
}
