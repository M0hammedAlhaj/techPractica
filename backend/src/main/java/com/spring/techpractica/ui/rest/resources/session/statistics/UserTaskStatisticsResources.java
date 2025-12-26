package com.spring.techpractica.ui.rest.resources.session.statistics;

import com.spring.techpractica.core.session.entity.statistics.UserTaskStatistics;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UserTaskStatisticsResources {

    private UUID userId;
    private String name;
    private long assignedTasksCount;
    private long onTimeTasksCount;

    public UserTaskStatisticsResources(UserTaskStatistics userTaskStatistics) {
        this.userId = userTaskStatistics.getUser().getId();
        this.name = userTaskStatistics.getUser().getFirstName() + " "
                + userTaskStatistics.getUser().getLastName();
        this.assignedTasksCount = userTaskStatistics.getAssignedTasksCount();
        this.onTimeTasksCount = userTaskStatistics.getOnTimeTasksCount();
    }
}
