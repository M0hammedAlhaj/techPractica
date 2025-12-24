package com.spring.techpractica.ui.rest.controller.notification;

import com.spring.techpractica.application.notification.get.GetNotificationCommand;
import com.spring.techpractica.application.notification.get.GetNotificationUseCase;
import com.spring.techpractica.core.notification.entity.Notification;
import com.spring.techpractica.core.user.UserAuthentication;
import com.spring.techpractica.ui.rest.resources.notification.NotificationCollection;
import com.spring.techpractica.ui.rest.resources.session.SessionCollection;
import com.spring.techpractica.ui.rest.shared.StandardSuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/notifications")
@Tag(name = "notifications")
public class NotificationController {

    private final GetNotificationUseCase getNotificationUseCase;

    @GetMapping("/")
    public ResponseEntity<?> getNotificationByUserId(@AuthenticationPrincipal UserAuthentication userAuthentication,
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastSeen) {

        List<Notification> notifications = getNotificationUseCase
                .execute(new GetNotificationCommand(userAuthentication.getUserId(), lastSeen));

        NotificationCollection response = new NotificationCollection(notifications);

        return ResponseEntity.ok(StandardSuccessResponse.<NotificationCollection>builder()
                .data(response)
                .message("Retrieved notifications successfully executed")
                .status(HttpStatus.OK.value())
                .build());
    }
}
