package com.spring.techpractica.infrastructure.jpa.notification;

import com.spring.techpractica.core.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaNotification extends JpaRepository<Notification, UUID> {
}
