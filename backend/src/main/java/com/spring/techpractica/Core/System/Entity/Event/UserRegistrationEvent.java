package com.spring.techpractica.Core.System.Entity.Event;

import java.util.UUID;

public record UserRegistrationEvent(UUID userId, String email, String name) {
}
