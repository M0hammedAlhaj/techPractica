package com.spring.techpractica.infrastructure.jpa.notification;

import com.spring.techpractica.core.notification.NotificationRepository;
import com.spring.techpractica.core.notification.entity.Notification;
import com.spring.techpractica.core.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
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

    @Override
    public List<Notification> findByUserAndAtCreatedAfterOrderByAtCreatedDesc(User user, LocalDateTime lastSeen) {
        return jpaNotification.findByUserAndAtCreatedAfterOrderByAtCreatedDesc(user, lastSeen);
    }
}
