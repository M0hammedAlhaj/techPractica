package com.spring.techpractica.application.notification.get;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetNotificationCommand(UUID userId, LocalDateTime lastSeen) {
}
