package com.spring.techpractica.ui.rest.resources.session.statistics;

import com.spring.techpractica.core.session.entity.statistics.SessionStatistics;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class SessionStatisticsResources {

    private UUID sessionId;
    private long totalTasks;
    private long duration;
    private List<UserTaskStatisticsResources> userTaskStatisticsResources;

    public SessionStatisticsResources(SessionStatistics sessionStatistics) {
        this.sessionId = sessionStatistics.getSession().getId();
        this.totalTasks = sessionStatistics.getTotalTasks();
        this.duration = sessionStatistics.getSessionDuration().toDays();

        this.userTaskStatisticsResources =
                sessionStatistics.getUserStats()
                        .stream()
                        .map(UserTaskStatisticsResources::new)
                        .collect(Collectors.toList());
    }
}
