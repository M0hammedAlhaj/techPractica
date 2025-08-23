package com.spring.techpractica.infrastructure.Jwt.Validation;


import java.util.UUID;

public record JwtValidationContext(String token, UUID expectedUserId) {
}
