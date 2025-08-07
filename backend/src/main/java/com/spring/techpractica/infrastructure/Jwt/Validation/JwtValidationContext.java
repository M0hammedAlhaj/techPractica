package com.spring.techpractica.infrastructure.Jwt.Validation;


public record JwtValidationContext(String token, String expectedUserId) {
}
