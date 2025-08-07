package com.spring.techpractica.infrastructure.Jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
@Getter
public class JwtGeneration {
    private final SecretKey secretKey;

    public JwtGeneration() {
        String key = "2XwD8eRxMvT4n9VpLF9UyBG5cD7NlKmKqY6fO9qxMzU=";
        this.secretKey = Keys.hmacShaKeyFor
                (Decoders.BASE64.decode(key));

    }

    public String generateToken(UUID id,String email) {

        return Jwts
                .builder()
                .subject(id.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()
                        + 60 * 60 * 60 * 1000))
                .signWith(secretKey)
                .issuer(email)
                .compact();
    }
}
