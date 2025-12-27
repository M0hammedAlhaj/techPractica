package com.spring.techpractica.application.notification.get;

import com.spring.techpractica.core.notification.NotificationRepository;
import com.spring.techpractica.core.notification.entity.Notification;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetNotificationUseCase {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public List<Notification> execute(GetNotificationCommand command) {
        User user = userRepository.getOrThrowByID(command.userId());

        return notificationRepository.findByUserAndAtCreatedAfterOrderByAtCreatedDesc(user, command.lastSeen());
    }
}