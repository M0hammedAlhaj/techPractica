package com.spring.techpractica.core.session.entity.statistics;

import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "SESSION_STATISTICS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionStatistics extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "session_id", referencedColumnName = "id", unique = true)
    private Session session;

    @Column(name = "total_tasks")
    private long totalTasks;

    @Column(name = "total_done_tasks")
    private long totalDoneTasks;

    @OneToMany(
            mappedBy = "sessionStatistics",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserTaskStatistics> userStats;

    @Column(name = "session_duration")
    private Duration sessionDuration;

    public void duration(Duration duration) {
        this.sessionDuration = duration;
    }
}
