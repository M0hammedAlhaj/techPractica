package com.spring.techpractica.application.session.request.get.sessions.requests;

import java.util.UUID;

public record GetSessionsRequestsCommand(UUID userId,
                                         UUID sessionId) {
}
