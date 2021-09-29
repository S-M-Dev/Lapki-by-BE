package com.smdev.lapkibe.service.impl;

import com.smdev.lapkibe.mapper.UserMapper;
import com.smdev.lapkibe.model.dto.UserRegistrationDTO;
import com.smdev.lapkibe.model.entity.UserEntity;
import com.smdev.lapkibe.repository.UserRepository;
import com.smdev.lapkibe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    //Todo Change to JWT response
    @Override
    public boolean registerUser(UserRegistrationDTO userRegistrationDTO) {
        if(getUserByEmail(userRegistrationDTO.getEmail()).isPresent()){
            return false;
        }

        userRepository.save(userMapper.registrationToEntity(userRegistrationDTO));
        return true;
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
