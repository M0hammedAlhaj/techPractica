package com.spring.techpractica.infrastructure.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
@AllArgsConstructor
public class JwtExtracting {
    private final JwtGeneration generation;

    public Claims parseToken(String token) {
        return Jwts.parser().verifyWith(generation.getSecretKey())
                .build().parseSignedClaims(token).getPayload();
    }

    private <T> T extractClaim(String token,
                               Function<Claims, T> claimsResolver) {
        final Claims claims = parseToken(token);
        return claimsResolver.apply(claims);
    }

    public String extractId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getIssuer);
    }

    public Date extractExpireDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
