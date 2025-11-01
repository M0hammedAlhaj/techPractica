package com.spring.techpractica.application.session.task.update;

import com.spring.techpractica.core.field.FieldRepository;
import com.spring.techpractica.core.field.entity.Field;
import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.shared.Exception.UnauthorizedActionException;
import com.spring.techpractica.core.task.TaskRepository;
import com.spring.techpractica.core.task.TaskService;
import com.spring.techpractica.core.task.entity.Task;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateTaskUseCase {

    private final SessionRepository sessionRepository;
    private final TaskRepository taskRepository;
    private final TaskService taskService;
    @Transactional
    public Task execute(UpdateTaskCommand command) {
        Session session = sessionRepository.getOrThrowByID(command.sessionId());
        taskService.validateSessionOwnership(session, command.ownerId());

        List<Field> fields = taskService.validateAndGetFields(command.tags());
        List<User> assignees = taskService.validateAndGetAssignees(command.assignees());
        Task task = taskRepository.getOrThrowByID(command.taskId());

        task.setDescription(command.description());
        task.setTitle(command.title());
        task.setUsersAssigned(assignees);
        task.setType(command.type());
        task.setDueDate(command.dueDate());
        task.setFields(fields);

        return taskRepository.save(task);
    }
}
