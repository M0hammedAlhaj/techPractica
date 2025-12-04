package com.spring.techpractica.infrastructure.jpa.notification;

import com.spring.techpractica.core.notification.NotificationRepository;
import com.spring.techpractica.core.notification.entity.Notification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaNotificationRepository implements NotificationRepository {

    private final JpaNotification jpaNotification;

    @Override
    public Notification save(Notification notification) {
        return jpaNotification.save(notification);
    }

    @Override
    public Notification update(Notification notification) {
        return null;
    }

    @Override
    public Optional<Notification> findById(UUID id) {
        return Optional.empty();
    }
}
