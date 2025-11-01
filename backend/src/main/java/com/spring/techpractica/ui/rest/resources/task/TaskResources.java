package com.spring.techpractica.ui.rest.resources.task;


import com.spring.techpractica.core.field.entity.Field;
import com.spring.techpractica.core.shared.BaseEntity;
import com.spring.techpractica.core.task.entity.Task;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class TaskResources {

    private final UUID id;
    private final String title;
    private final String description;
    private final String type;
    private final LocalDateTime dueDate;
    private final List<UUID> assignees;
    private final List<String> tags;

    public TaskResources(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.type = task.getType().name();
        this.dueDate = task.getDueDate();

        this.assignees = task.getUsersAssigned()
                .stream()
                .map(BaseEntity::getId)
                .toList();

        this.tags = task.getFields()
                .stream()
                .map(Field::getName)
                .toList();
    }
}
