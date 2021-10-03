package com.smdev.lapkibe.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JWTUtil {
    private final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;

    @Getter
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.token.duration}")
    private int time;
    @Value("${jwt.token.prefix}")
    private String prefix;
    
    private boolean isExpired(String token){
        return getClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T getClaim(String token, Function<Claims, T> func){
        return func.apply(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody());
    }

    public String generate(UserDetails userDetails){
        return prefix + Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .signWith(ALGORITHM, secret)
                .compact();
    }

    public boolean validate(String token, UserDetails userDetails){
        return (!isExpired(token) && getClaim(token, Claims::getSubject).equals(userDetails.getUsername()));
    }

    public String gerEmail(String token){
        return getClaim(token, Claims::getSubject);
    }

}
