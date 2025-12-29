package com.spring.techpractica.core.shared.Exception;

public class TaskDueDatePassedException extends RuntimeException {
    public TaskDueDatePassedException() {
        super("Task due date has already passed");
    }
}
