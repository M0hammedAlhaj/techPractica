package com.spring.techpractica.application.session.delete;

import java.util.UUID;

public record DeleteSessionCommand(UUID userId, UUID sessionId) {
}