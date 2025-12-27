package com.spring.techpractica.application.session.task.get;

import java.util.UUID;

public record GetTaskCommand(UUID userId, UUID sessionId) {
}