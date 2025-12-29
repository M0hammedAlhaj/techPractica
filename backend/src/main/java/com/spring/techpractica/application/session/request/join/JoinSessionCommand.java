package com.spring.techpractica.application.session.request.join;

import java.util.UUID;

public record JoinSessionCommand(UUID userId,
                                 UUID sessionId) {
}
