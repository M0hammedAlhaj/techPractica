package com.spring.techpractica.ui.rest.resources.task;

import com.spring.techpractica.core.task.entity.Task;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TaskCollection {
private final List<TaskResources> tasks;
    public TaskCollection(List<Task> tasks) {
        this.tasks = tasks.stream().map(TaskResources::new).collect(Collectors.toList());
    }
}
