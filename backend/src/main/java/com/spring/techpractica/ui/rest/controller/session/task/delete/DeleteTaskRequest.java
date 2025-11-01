package com.spring.techpractica.ui.rest.controller.session.task.delete;

import java.util.UUID;

public record DeleteTaskRequest(UUID sessionId, UUID taskId) { }