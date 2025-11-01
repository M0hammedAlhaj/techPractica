package com.spring.techpractica.core.notification.entity;

import com.spring.techpractica.core.shared.BaseEntity;
import com.spring.techpractica.core.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "Notifications")
public class Notification extends BaseEntity {
    @Column(name = "notfication_content")
    private String NotificationsContent;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "is_read")
    private boolean isRead;
}
