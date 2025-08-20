package com.spring.techpractica.Core.Field.Entity.Event;

import java.util.UUID;

public record UserRegistrationEvent(UUID userId, String email, String name) {
}
