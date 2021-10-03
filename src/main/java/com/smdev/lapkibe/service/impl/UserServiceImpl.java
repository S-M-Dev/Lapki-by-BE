package com.smdev.lapkibe.service.impl;

import com.smdev.lapkibe.mapper.UserMapper;
import com.smdev.lapkibe.model.dto.UserLoginDTO;
import com.smdev.lapkibe.model.dto.UserRegistrationDTO;
import com.smdev.lapkibe.model.entity.UserEntity;
import com.smdev.lapkibe.repository.UserRepository;
import com.smdev.lapkibe.service.UserService;
import com.smdev.lapkibe.util.JWTUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public String loginUser(UserLoginDTO userLoginDTO) {
        Optional<UserEntity> userEntityOptional = getUserByEmail(userLoginDTO.getEmail());

        if(userEntityOptional.isEmpty() || !userEntityOptional
                .get()
                .getPassword()
                .equals(userLoginDTO.getPassword())){
            return new String();
        }

        authenticate(userEntityOptional.get().getEmail());
        return jwtUtil.generate(userDetailsService.loadUserByUsername(userEntityOptional.get().getEmail()));
    }

    @Override
    public String registerUser(UserRegistrationDTO userRegistrationDTO) {
        if(getUserByEmail(userRegistrationDTO.getEmail()).isPresent()){
            return new String();
        }

        userRepository.save(userMapper.registrationToEntity(userRegistrationDTO));
        authenticate(userRegistrationDTO.getEmail());
        return jwtUtil.generate(userDetailsService.loadUserByUsername(userRegistrationDTO.getEmail()));
    }

    @Override
    public void authenticate(String email) {
        Optional<UserEntity> userEntityOptional = getUserByEmail(email);
        if(userEntityOptional.isEmpty()){
            LOGGER.error(String.format("Unable to authenticate user '%s'. User not found!", email));
            return;
        }
        SecurityContextHolder
                .getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                email,
                                userEntityOptional.get().getPassword(),
                                List.of(new SimpleGrantedAuthority(userEntityOptional.get().getRole().name()))
                                )
                );
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
