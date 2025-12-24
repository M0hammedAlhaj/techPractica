package com.spring.techpractica.application.session.task.create;

import com.spring.techpractica.application.notification.CreateNotificationUseCase;
import com.spring.techpractica.core.field.entity.Field;
import com.spring.techpractica.core.notification.NotificationRepository;
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
    private final CreateNotificationUseCase createNotificationUseCase;
    private final NotificationRepository notificationRepository;


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

        Task savedTask = taskRepository.save(task);

        String title = "New Task Assigned";
        String content = "You have been assigned a new task: " + task.getTitle()
                + " in session: " + session.getName();

        assignees.forEach(assignee -> {
            if (!user.getId().equals(assignee.getId())) {
                notificationRepository.save(
                        createNotificationUseCase.execute(assignee, title, content)
                );
            }
        });

        return savedTask;
    }
}
