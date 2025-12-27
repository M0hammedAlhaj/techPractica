package com.spring.techpractica.ui.rest.resources.notification;

import com.spring.techpractica.core.notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class NotificationResources {

    private UUID notificationId;
    private String title;
    private String content;

    public  NotificationResources(Notification notification) {
        this.notificationId = notification.getId();
        this.title = notification.getTitle();
        this.content = notification.getContent();
    }
}