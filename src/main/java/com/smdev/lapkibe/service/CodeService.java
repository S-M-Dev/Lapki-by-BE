package com.smdev.lapkibe.service;

import com.smdev.lapkibe.model.dto.PasswordResetRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface CodeService {
    boolean send(PasswordResetRequestDTO passwordResetRequestDTO);
}
