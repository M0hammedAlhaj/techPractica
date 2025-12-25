package com.spring.techpractica.ui.rest.resources.session.statistics;

import com.spring.techpractica.core.session.entity.statistics.UserTaskStatistics;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserTaskStatisticsResources {

    private UUID userId;
    private String userName;
    private long assignedTasksCount;
    private long onTimeTasksCount;

    public UserTaskStatisticsResources(UserTaskStatistics userTaskStatistics) {
        this.userId = userTaskStatistics.getUser().getId();
        this.userName = userTaskStatistics.getUser().getName();
        this.assignedTasksCount = userTaskStatistics.getAssignedTasksCount();
        this.onTimeTasksCount = userTaskStatistics.getOnTimeTasksCount();
    }
}
