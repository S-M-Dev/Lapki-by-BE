package com.smdev.lapkibe.service;

import com.smdev.lapkibe.model.dto.PasswordResetRequestDTO;
import com.smdev.lapkibe.model.dto.ResetCodeValidationsRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CodeService {
    boolean send(PasswordResetRequestDTO passwordResetRequestDTO);
    ResponseEntity validate(ResetCodeValidationsRequest resetCodeValidationsRequest);
}
