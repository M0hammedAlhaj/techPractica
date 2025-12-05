package com.spring.techpractica.core.shared.Exception;

import java.util.UUID;

public class UserAlreadyMemberException extends RuntimeException {
    public UserAlreadyMemberException(UUID userId, UUID sessionId) {
        super("User with ID " + userId + " is already a member of session " + sessionId);
    }

    public UserAlreadyMemberException(String message) {
        super(message);
    }}
