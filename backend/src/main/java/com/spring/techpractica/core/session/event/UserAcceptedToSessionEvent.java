package com.spring.techpractica.core.session.event;

import java.util.UUID;

public record UserAcceptedToSessionEvent(UUID userId,
                                         String name,
                                         String email,
                                         UUID sessionId,
                                         String sessionName) {
}
