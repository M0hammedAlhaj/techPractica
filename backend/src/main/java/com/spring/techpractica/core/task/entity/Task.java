package com.spring.techpractica.core.task.entity;

import com.spring.techpractica.core.task.model.TaskStatus;
import com.spring.techpractica.core.task.model.TaskType;
import com.spring.techpractica.core.field.entity.Field;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.shared.BaseEntity;
import com.spring.techpractica.core.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "TASKS")
public class Task extends BaseEntity {

    @Column(name = "task_title")
    private String title;

    @Column(name = "task_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_owner_id", referencedColumnName = "id")
    private User userOwnerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    private Session session;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "task_id")
            , inverseJoinColumns = @JoinColumn(name = "user_assign_id", referencedColumnName = "id")
    )
    private List<User> usersAssigned;

    private LocalDateTime dueDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.TO_DO;

    @Column(name = "Task_Type")
    @Enumerated(EnumType.STRING)
    private TaskType type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "task_id"),
                inverseJoinColumns = @JoinColumn(name = "field_id", referencedColumnName = "id"))
    private List<Field> fields;

    public void updateStatus(TaskStatus status) {
        this.status = status;
    }
}