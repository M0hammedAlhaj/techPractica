package com.spring.techpractica.application.session.task.get;

import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.task.TaskRepository;
import com.spring.techpractica.core.task.TaskService;
import com.spring.techpractica.core.task.entity.Task;
import com.spring.techpractica.core.task.model.TaskStatus;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
public class GetTaskUseCase {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @Transactional
    public List<Task> execute(GetTaskCommand command) {
        User user = userRepository.getOrThrowByID(command.userId());

        Session session = sessionRepository.getOrThrowByID(command.sessionId());

        taskService.validateSessionParticipant(session, user.getId());

        return session.getTasks();
    }
}