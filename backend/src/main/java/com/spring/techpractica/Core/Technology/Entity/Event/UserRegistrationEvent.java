package com.spring.techpractica.Core.Technology.Entity.Event;

import java.util.UUID;

public record UserRegistrationEvent(UUID userId, String email, String name) {
}
