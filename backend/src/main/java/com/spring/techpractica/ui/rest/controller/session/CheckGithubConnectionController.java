package com.spring.techpractica.ui.rest.controller.session;

import com.spring.techpractica.application.session.CheckGithubConnectionUseCase;
import com.spring.techpractica.application.session.get.user.sessions.GetUserSessionsUseCase;
import com.spring.techpractica.core.user.UserAuthentication;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sessions/github")
@AllArgsConstructor
@Tag(name = "sessions")
public class CheckGithubConnectionController {

    private final CheckGithubConnectionUseCase checkGithubConnectionUseCase;

    @GetMapping("/connection")
    public ResponseEntity<?> checkGithubConnection(@AuthenticationPrincipal UserAuthentication userAuthentication) {
        String connected = checkGithubConnectionUseCase.execute(userAuthentication.getUserId());

        return ResponseEntity.ok(connected);
    }
}