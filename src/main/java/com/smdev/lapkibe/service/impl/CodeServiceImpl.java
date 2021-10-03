package com.smdev.lapkibe.service.impl;

import com.smdev.lapkibe.model.dto.PasswordResetRequestDTO;
import com.smdev.lapkibe.repository.CodeRepository;
import com.smdev.lapkibe.service.CodeService;
import com.smdev.lapkibe.service.MailService;
import com.smdev.lapkibe.service.UserService;
import com.smdev.lapkibe.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeServiceImpl implements CodeService {

    private final CodeRepository codeRepository;
    private final UserService userService;
    private final MailService mailService;

    @Autowired
    public CodeServiceImpl(CodeRepository codeRepository, UserService userService, MailService mailService) {
        this.codeRepository = codeRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    @Override
    public boolean send(PasswordResetRequestDTO passwordResetRequestDTO) {
        if(userService.getUserByEmail(passwordResetRequestDTO.getEmail()).isEmpty()){
            return false;
        }

        mailService.sendMail("Password restore code [Lapki.by]",
                String.format("Your restore code is %s", CodeUtil.generate()),
                passwordResetRequestDTO.getEmail());
        return true;
    }
}
