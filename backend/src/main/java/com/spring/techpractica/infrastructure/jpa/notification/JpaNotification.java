package com.spring.techpractica.infrastructure.jpa.notification;

import com.spring.techpractica.core.notification.entity.Notification;
import com.spring.techpractica.core.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface JpaNotification extends JpaRepository<Notification, UUID> {
    List<Notification> findByUserAndAtCreatedAfterOrderByAtCreatedAsc(User user, LocalDateTime atCreatedAfter);
}
