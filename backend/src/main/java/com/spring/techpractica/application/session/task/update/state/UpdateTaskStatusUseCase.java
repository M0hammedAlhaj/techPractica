package com.spring.techpractica.application.session.task.update.state;

import com.spring.techpractica.core.task.TaskRepository;
import com.spring.techpractica.core.task.entity.Task;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateTaskStatusUseCase {

    private final TaskRepository taskRepository;

    @Transactional
    public Task execute(UpdateTaskStatusCommand command) {

        Task task = taskRepository.getOrThrowByID(command.taskId());

        task.updateStatus(command.status());

        return taskRepository.save(task);
    }
}
