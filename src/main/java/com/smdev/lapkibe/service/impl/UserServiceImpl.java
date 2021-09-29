package com.smdev.lapkibe.service.impl;

import com.smdev.lapkibe.mapper.UserMapper;
import com.smdev.lapkibe.model.dto.UserLoginDTO;
import com.smdev.lapkibe.model.dto.UserRegistrationDTO;
import com.smdev.lapkibe.model.entity.UserEntity;
import com.smdev.lapkibe.repository.UserRepository;
import com.smdev.lapkibe.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    //Todo Change to JWT response
    @Override
    public boolean loginUser(UserLoginDTO userLoginDTO) {
        Optional<UserEntity> userEntityOptional = getUserByEmail(userLoginDTO.getEmail());

        if(userEntityOptional.isEmpty() || !userEntityOptional
                .get()
                .getPassword()
                .equals(userLoginDTO.getPassword())){
            return false;
        }

        authenticate(userEntityOptional.get().getEmail());
        return true;
    }

    //Todo Change to JWT response
    @Override
    public boolean registerUser(UserRegistrationDTO userRegistrationDTO) {
        if(getUserByEmail(userRegistrationDTO.getEmail()).isPresent()){
            return false;
        }

        userRepository.save(userMapper.registrationToEntity(userRegistrationDTO));
        authenticate(userRegistrationDTO.getEmail());
        return true;
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
