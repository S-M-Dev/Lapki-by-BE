package com.smdev.lapkibe.service.impl;

import java.util.Optional;

import com.smdev.lapkibe.model.dto.JwtUserDetails;
import com.smdev.lapkibe.model.dto.PasswordResetRequestDTO;
import com.smdev.lapkibe.model.entity.CodeEntity;
import com.smdev.lapkibe.model.entity.UserEntity;
import com.smdev.lapkibe.repository.CodeRepository;
import com.smdev.lapkibe.service.CodeService;
import com.smdev.lapkibe.service.MailService;
import com.smdev.lapkibe.service.UserService;
import com.smdev.lapkibe.util.CodeUtil;
import com.smdev.lapkibe.util.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CodeServiceImpl implements CodeService {

    private final JwtUserDetailsServiceImpl jwtUserDetailsService;
    private final CodeRepository codeRepository;
    private final UserService userService;
    private final MailService mailService;
    private final JWTUtil jwtUtil;

    @Autowired
    public CodeServiceImpl(JwtUserDetailsServiceImpl jwtUserDetailsService, CodeRepository codeRepository,
            UserService userService, MailService mailService, JWTUtil jwtUtil) {
        this.codeRepository = codeRepository;
        this.userService = userService;
        this.mailService = mailService;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtUtil = jwtUtil;
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

    @Override
    public ResponseEntity validate(String email, String code, String newPassword) {
        Optional<UserEntity> user = userService.getUserByEmail(email);
        Optional<CodeEntity> actualCode = codeRepository.findByEmail(email);

        if(user.isEmpty() || actualCode.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        if(!actualCode.get().getCode().equals(code)){
            return ResponseEntity.accepted().build();
        }

        codeRepository.delete(actualCode.get());
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(email);
        userService.authenticate(userDetails);
        return ResponseEntity.ok(jwtUtil.generate(userDetails));
    }
}
