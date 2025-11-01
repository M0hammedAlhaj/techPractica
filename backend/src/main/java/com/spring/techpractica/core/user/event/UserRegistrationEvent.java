package com.spring.techpractica.core.user.event;

import java.util.UUID;

public record UserRegistrationEvent(UUID userId, String email, String name) {
}
