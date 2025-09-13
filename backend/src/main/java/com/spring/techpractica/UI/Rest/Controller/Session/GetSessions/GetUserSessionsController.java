package com.spring.techpractica.UI.Rest.Controller.Session.GetSessions;

import com.spring.techpractica.Application.Session.GetSessions.GetUserSessionsUseCase;
import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.User.UserAuthentication;
import com.spring.techpractica.UI.Rest.Resources.Session.SessionCollection;
import com.spring.techpractica.UI.Rest.Shared.StandardSuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
@AllArgsConstructor
public class GetUserSessionsController {

    private final GetUserSessionsUseCase getUserSessionsUseCase;

    @GetMapping("/by-user")
    public ResponseEntity<?> getSessionsByUser(@AuthenticationPrincipal UserAuthentication userAuthentication) {
        UUID userId = userAuthentication.getUserId();

        List<Session> userSessions = getUserSessionsUseCase.execute(userId);

        SessionCollection sessionCollection = new SessionCollection(userSessions);

        return ResponseEntity.ok(StandardSuccessResponse.<SessionCollection>builder()
                .data(sessionCollection)
                .message("Get User Sessions Successfully executed")
                .status(HttpStatus.OK.value())
                .build());
    }
}