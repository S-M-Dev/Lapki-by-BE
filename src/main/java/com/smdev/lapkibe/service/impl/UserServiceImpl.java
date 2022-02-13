package com.smdev.lapkibe.service.impl;

import com.smdev.lapkibe.mapper.UserMapper;
import com.smdev.lapkibe.model.dto.PasswordChangeDTO;
import com.smdev.lapkibe.model.dto.UserLoginDTO;
import com.smdev.lapkibe.model.dto.UserRegistrationDTO;
import com.smdev.lapkibe.model.dto.UserResponseDTO;
import com.smdev.lapkibe.model.dto.UserUpdateRequest;
import com.smdev.lapkibe.model.entity.UserEntity;
import com.smdev.lapkibe.repository.UserRepository;
import com.smdev.lapkibe.service.UserService;
import com.smdev.lapkibe.util.JWTUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           JWTUtil jwtUtil,
                           UserDetailsService userDetailsService,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String loginUser(UserLoginDTO userLoginDTO) {
        Optional<UserEntity> userEntityOptional = getUserByEmail(userLoginDTO.getEmail());

        if(userEntityOptional.isEmpty()){
            return new String();
        }

        String password = userLoginDTO.getPassword();

        if(!passwordEncoder.matches(password, userEntityOptional.get().getPassword())){
            return new String();
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEntityOptional.get().getEmail());
        authenticate(userDetails);
        return jwtUtil.generate(userDetails);
    }

    @Override
    public String registerUser(UserRegistrationDTO userRegistrationDTO) {
        if(getUserByEmail(userRegistrationDTO.getEmail()).isPresent()){
            return new String();
        }

        UserEntity userEntity = userMapper.registrationToEntity(userRegistrationDTO);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userRegistrationDTO.getEmail());
        authenticate(userDetails);
        return jwtUtil.generate(userDetails);
    }

    @Override
    public void authenticate(UserDetails userDetails) {
        Optional<UserEntity> userEntityOptional = getUserByEmail(userDetails.getUsername());
        if(userEntityOptional.isEmpty()){
            LOGGER.error(String.format("Unable to authenticate user '%s'. User not found!", userDetails.getUsername()));
            return;
        }
        SecurityContextHolder
                .getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                userDetails.getUsername(),
                                userEntityOptional.get().getPassword(),
                                userDetails.getAuthorities()
                                )
                );
    }

    @Override
    public String changePassword(PasswordChangeDTO passwordChangeDTO) {
        String newPassword = passwordEncoder.encode(passwordChangeDTO.getPassword());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserEntity> userEntity = getUserByEmail(email);

        if(userEntity.isEmpty()){
            return new String();
        }

        UserEntity user = userEntity.get();
        user.setPassword(newPassword);
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        authenticate(userDetails);
        return jwtUtil.generate(userDetails);
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserResponseDTO getCurrent() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponseDTO updateUser(final UserUpdateRequest userUpdateRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByEmail(email).get();
        user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        user.setEmail(userUpdateRequest.getEmail());
        user.setFullName(userUpdateRequest.getFullName());
        user.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        userRepository.save(user);
        return userMapper.toResponse(user);
    }
}
