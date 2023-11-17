package com.dottree.nonogrammers.config.jwt;

import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
@Setter
@Getter
public class JwtProperties {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.issuer}")
    private String ISSUER;

    // 임시 키
    private SecretKey KEY = Jwts.SIG.HS256.key().build();

    private int EXPIRATION_TIME = 86400000; // 1일 (1/1000초)
    private String TOKEN_PREFIX = "Bearer ";
    private String HEADER_STRING = "Authorization";
}
