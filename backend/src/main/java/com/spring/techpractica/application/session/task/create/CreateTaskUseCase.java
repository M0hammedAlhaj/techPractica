package com.spring.techpractica.application.session.task.create;

import com.spring.techpractica.core.field.entity.Field;
import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.task.TaskRepository;
import com.spring.techpractica.core.task.TaskService;
import com.spring.techpractica.core.task.entity.Task;
import com.spring.techpractica.core.task.model.TaskStatus;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateTaskUseCase {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @Transactional
    public Task execute(CreateTaskCommand command) {
        Session session = sessionRepository.getOrThrowByID(command.sessionId());
        taskService.validateSessionParticipant(session, command.userId());

        List<Field> fields = taskService.validateAndGetFields(command.tags());
        User user = userRepository.getOrThrowByID(command.userId());
        List<User> assignees = taskService.validateAndGetAssignees(command.assignees());

        Task task = new Task(
                command.title(),
                command.description(),
                user,
                session,
                assignees,
                command.dueDate(),
                TaskStatus.TO_DO,
                command.type(),
                fields
        );
        return taskRepository.save(task);
    }
}
