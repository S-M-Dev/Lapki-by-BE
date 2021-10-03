package com.smdev.lapkibe.model.dto;

import com.smdev.lapkibe.model.entity.UserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class JwtUserDetails implements UserDetails {

    private final String email;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public JwtUserDetails(UserEntity userEntity){
        this.email = userEntity.getEmail();
        this.password = userEntity.getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority(userEntity.getRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
