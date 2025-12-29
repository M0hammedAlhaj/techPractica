package com.spring.techpractica.ui.rest.resources.notification;

import com.spring.techpractica.core.notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class NotificationCollection {

    private final List<NotificationResources> notifications;

    public NotificationCollection(List<Notification> notifications) {
        this.notifications = notifications.stream()
                .map(NotificationResources::new)
                .toList();
    }
}
