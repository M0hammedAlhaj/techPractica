package com.spring.techpractica.application.session.task.create;

import com.spring.techpractica.core.task.model.TaskType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record CreateTaskCommand (UUID userId, UUID sessionId, String title,
                                 String description, TaskType type, LocalDateTime dueDate,
                                 Set<UUID> assignees, Set<String> tags){
}