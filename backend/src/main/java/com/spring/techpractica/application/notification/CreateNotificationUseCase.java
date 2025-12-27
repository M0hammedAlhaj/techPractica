package com.spring.techpractica.application.notification;

import com.spring.techpractica.core.notification.NotificationRepository;
import com.spring.techpractica.core.notification.entity.Notification;
import com.spring.techpractica.core.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateNotificationUseCase {

    private final NotificationRepository notificationRepository;

    public Notification execute(User user, String title, String content) {
        Notification notification = new Notification(title, content, user, false);
        return notificationRepository.save(notification);
    }
}