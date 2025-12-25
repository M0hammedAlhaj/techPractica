package com.spring.techpractica.application.session.finish;

import java.util.UUID;

public record FinishSessionCommand(UUID ownerId, UUID sessionId) {
}
