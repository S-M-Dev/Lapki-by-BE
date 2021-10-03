package com.smdev.lapkibe.service.impl;

import com.smdev.lapkibe.model.dto.JwtUserDetails;
import com.smdev.lapkibe.model.entity.UserEntity;
import com.smdev.lapkibe.service.UserService;
import com.smdev.lapkibe.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<UserEntity> userEntityOptional = userService.getUserByEmail(s);
        UserEntity user = userEntityOptional.orElseThrow(() -> new UsernameNotFoundException(s));
        return new JwtUserDetails(user);
    }
}
