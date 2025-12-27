package com.spring.techpractica.application.session.start;

import java.util.UUID;

public record StartSessionCommand(UUID userId, UUID sessionId) {
}