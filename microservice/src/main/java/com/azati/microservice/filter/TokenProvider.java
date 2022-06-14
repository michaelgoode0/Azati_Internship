package com.azati.microservice.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Configuration
@PropertySource("classpath:security.properties")
public class TokenProvider implements InitializingBean {
    public static final String DEFAULT_TOKEN_TYPE="Bearer ";
    private static final String AUTHORITIES_KEY="auth";

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.token-validity}")
    private Long tokenValidity;

    private Key key;


    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        this.key= Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Date validity = new Date(new Date().getTime()+tokenValidity);
        return DEFAULT_TOKEN_TYPE + Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY,authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }
}
