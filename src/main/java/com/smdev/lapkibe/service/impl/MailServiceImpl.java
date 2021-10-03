package com.smdev.lapkibe.service.impl;

import com.smdev.lapkibe.model.dto.SendMailDTO;
import com.smdev.lapkibe.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;
    @Value("${mail.admin}")
    private String adminEmail;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendContactUsMail(SendMailDTO sendMailDTO) {
        sendMail(String.format("Contact (%s <%s> <%s>)",
                    sendMailDTO.getFullName(),
                    sendMailDTO.getEmail(),
                    sendMailDTO.getNumber()),
                sendMailDTO.getMessage(),
                adminEmail);
    }

    @Override
    public void sendMail(String subject, String message, String to){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(senderEmail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setTo(to);
        javaMailSender.send(simpleMailMessage);
    }
}
