package com.spring.techpractica.infrastructure.Jwt.Exception;

public class JwtValidationException extends RuntimeException {
    public JwtValidationException(String message) {
        super(message);
    }
}
