package com.spring.techpractica.application.session.get.sessions.statistics;

import java.util.UUID;

public record GetSessionStatisticsCommand(UUID userId, UUID sessionId) {
}
