package com.spring.techpractica.infrastructure.Jwt;

import com.spring.techpractica.infrastructure.Jwt.Exception.JwtValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
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

    public Optional<UUID> extractId(Optional<String> tokenOpt) {
        return tokenOpt
                .map(header -> header.replaceFirst("^Bearer\\s+", ""))
                .filter(t -> !t.isBlank())
                .map(t -> {
                    try {
                        String subject = extractClaim(t, Claims::getSubject);
                        return UUID.fromString(subject);
                    } catch (Exception e) {
                        throw new JwtValidationException("JWT ");
                    }
                });
    }

    public UUID extractId(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("JWT token not be null or empty");
        }
        String id = extractClaim(token, Claims::getSubject);
        return UUID.fromString(id);
    }

    public Date extractExpireDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
