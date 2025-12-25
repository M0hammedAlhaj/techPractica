package com.spring.techpractica.core.session.entity.statistics;

import com.spring.techpractica.core.shared.BaseEntity;
import com.spring.techpractica.core.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "USER_TASK_STATISTICS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskStatistics extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_statistics_id")
    private SessionStatistics sessionStatistics;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "assigned_tasks_count")
    private long assignedTasksCount;

    @Column(name = "on_time_tasks_count")
    private long onTimeTasksCount;
}
