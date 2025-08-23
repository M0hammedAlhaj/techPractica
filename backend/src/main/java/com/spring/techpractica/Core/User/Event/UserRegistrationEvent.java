package com.spring.techpractica.Core.User.Event;

import java.util.UUID;

public record UserRegistrationEvent(UUID userId, String email, String name) {
}
