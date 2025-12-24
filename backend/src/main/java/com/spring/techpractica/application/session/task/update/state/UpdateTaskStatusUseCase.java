package com.spring.techpractica.application.session.task.update.state;

import com.spring.techpractica.core.shared.Exception.TaskDueDatePassedException;
import com.spring.techpractica.core.task.TaskRepository;
import com.spring.techpractica.core.task.entity.Task;
import com.spring.techpractica.core.task.model.TaskStatus;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UpdateTaskStatusUseCase {

    private final TaskRepository taskRepository;

    @Transactional
    public Task execute(UpdateTaskStatusCommand command) {
        Task task = taskRepository.getOrThrowByID(command.taskId());

        if (command.status() == TaskStatus.DONE &&
                task.getDueDate() != null &&
                task.getDueDate().isBefore(LocalDateTime.now())) {
            throw new TaskDueDatePassedException();
        }
        task.updateStatus(command.status());

        return taskRepository.save(task);
    }
}
