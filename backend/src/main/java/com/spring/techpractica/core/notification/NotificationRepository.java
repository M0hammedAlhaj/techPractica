package com.spring.techpractica.core.notification;

import com.spring.techpractica.core.notification.entity.Notification;
import com.spring.techpractica.core.shared.BaseRepository;
import com.spring.techpractica.core.user.User;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends BaseRepository<Notification> {
    List<Notification> findByUserAndAtCreatedAfterOrderByAtCreatedAsc(
            User user,
            LocalDateTime lastSeen
    );
}
