package com.spring.techpractica.application.session.finish;

import com.spring.techpractica.core.session.SessionCannotBeFinishedException;
import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.session.SessionStatisticsRepository;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.entity.statistics.SessionStatistics;
import com.spring.techpractica.core.session.entity.statistics.UserTaskStatistics;
import com.spring.techpractica.core.shared.Exception.UnauthorizedActionException;
import com.spring.techpractica.core.task.model.TaskStatus;
import com.spring.techpractica.core.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinishSessionUseCase {

    private final SessionRepository sessionRepository;
    private final SessionStatisticsRepository  sessionStatisticsRepository;

    @Transactional
    public Session execute(FinishSessionCommand command) {

        Session session = sessionRepository.getOrThrowByID(command.sessionId());

        if (!session.isOwner(command.ownerId())) {
            throw new UnauthorizedActionException(
                    "User must be the session owner to finish the session."
            );
        }

        boolean hasUnfinishedTasks = session.getTasks().stream()
                .anyMatch(task -> task.getStatus() != TaskStatus.DONE);

        if (hasUnfinishedTasks) {
            throw new SessionCannotBeFinishedException(
                    "Cannot finish session. There are unfinished tasks."
            );
        }

        long totalTasksInSession = session.getTasks().size();

        Map<UUID, Long> tasksPerUser = session.getTasks().stream()
                .flatMap(task -> task.getUsersAssigned().stream())
                .collect(Collectors.groupingBy(
                        User::getId,
                        Collectors.counting()
                ));

        Map<UUID, Long> onTimeTasksPerUser = session.getTasks().stream()
                .filter(task -> task.getStatus() == TaskStatus.DONE)
                .filter(task -> !task.getAtModified().isAfter(task.getDueDate()))
                .flatMap(task -> task.getUsersAssigned().stream())
                .collect(Collectors.groupingBy(
                        User::getId,
                        Collectors.counting()
                ));

//        long totalDoneTasks = session.getTasks().stream()
//                .filter(task -> task.getStatus() == TaskStatus.DONE)
//                .count();

        SessionStatistics statistics = SessionStatistics.builder()
                .session(session)
                .totalTasks(totalTasksInSession)
//                .totalDoneTasks(totalDoneTasks)
                .build();

        List<UserTaskStatistics> userStats =
                tasksPerUser.entrySet().stream()
                        .map(entry -> UserTaskStatistics.builder()
                                .sessionStatistics(statistics)
                                .user(new User(entry.getKey()))
                                .assignedTasksCount(entry.getValue())
                                .onTimeTasksCount(
                                        onTimeTasksPerUser.getOrDefault(entry.getKey(), 0L)
                                )
                                .build()
                        )
                        .toList();

        statistics.setUserStats(userStats);

        Duration duration = Duration.between(
                session.getAtCreated(),
                LocalDateTime.now()
        );

        statistics.duration(duration);

        sessionStatisticsRepository.save(statistics);

        session.endSession();

        return sessionRepository.save(session);
    }

}
