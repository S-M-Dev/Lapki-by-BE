package com.smdev.lapkibe.service;

import com.smdev.lapkibe.model.dto.SendMailDTO;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendContactUsMail(SendMailDTO sendMailDTO);
    void sendMail(String subject, String message, String to);
}
