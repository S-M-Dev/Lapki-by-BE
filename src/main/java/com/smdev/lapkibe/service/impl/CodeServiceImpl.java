package com.smdev.lapkibe.service.impl;

import java.util.Optional;

import com.smdev.lapkibe.model.dto.JwtUserDetails;
import com.smdev.lapkibe.model.dto.PasswordResetRequestDTO;
import com.smdev.lapkibe.model.dto.ResetCodeValidationsRequest;
import com.smdev.lapkibe.model.entity.CodeEntity;
import com.smdev.lapkibe.model.entity.UserEntity;
import com.smdev.lapkibe.repository.CodeRepository;
import com.smdev.lapkibe.repository.UserRepository;
import com.smdev.lapkibe.service.CodeService;
import com.smdev.lapkibe.service.MailService;
import com.smdev.lapkibe.service.UserService;
import com.smdev.lapkibe.util.CodeUtil;
import com.smdev.lapkibe.util.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CodeServiceImpl implements CodeService {

    private final JwtUserDetailsServiceImpl jwtUserDetailsService;
    private final CodeRepository codeRepository;
    private final UserService userService;
    private final MailService mailService;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public CodeServiceImpl(JwtUserDetailsServiceImpl jwtUserDetailsService, CodeRepository codeRepository,
            UserService userService, MailService mailService, JWTUtil jwtUtil,
            PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.codeRepository = codeRepository;
        this.userService = userService;
        this.mailService = mailService;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
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
    public ResponseEntity validate(ResetCodeValidationsRequest resetCodeValidationsRequest) {
        Optional<UserEntity> user = userService.getUserByEmail(resetCodeValidationsRequest.getEmail());
        Optional<CodeEntity> actualCode = codeRepository.findByEmail(resetCodeValidationsRequest.getEmail());

        if(user.isEmpty() || actualCode.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        if(!actualCode.get().getCode().equals(resetCodeValidationsRequest.getCode())){
            return ResponseEntity.accepted().build();
        }

        codeRepository.delete(actualCode.get());
        user.get().setPassword(passwordEncoder.encode(resetCodeValidationsRequest.getNewPassword()));
        userRepository.save(user.get());

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(resetCodeValidationsRequest.getEmail());
        userService.authenticate(userDetails);

        return ResponseEntity.ok(jwtUtil.generate(userDetails));
    }
}
