package com.spring.techpractica.application.session.task.delete;

import java.util.UUID;

public record DeleteTaskCommand(UUID userId, UUID sessionId, UUID taskId) {
}