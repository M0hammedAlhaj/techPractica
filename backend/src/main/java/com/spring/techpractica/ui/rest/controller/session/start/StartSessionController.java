package com.spring.techpractica.ui.rest.controller.session.start;

import com.spring.techpractica.application.session.start.StartSessionCommand;
import com.spring.techpractica.application.session.start.StartSessionUseCase;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.user.UserAuthentication;
import com.spring.techpractica.ui.rest.resources.session.SessionResources;
import com.spring.techpractica.ui.rest.shared.StandardSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
@AllArgsConstructor
@Tag(name = "Sessions", description = "Endpoints for managing sessions")
public class StartSessionController {

    private final StartSessionUseCase startSessionUseCase;

    @Operation(
            summary = "Start a session",
            description = "Starts a session with the given session ID. The user must be authenticated."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session started successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Session not found")
    })
    @PutMapping("/start/{sessionId}")
    public ResponseEntity<?> invoke(@AuthenticationPrincipal UserAuthentication userAuthentication,
                                    @PathVariable UUID sessionId) {
        Session session = startSessionUseCase.execute(
                new StartSessionCommand(userAuthentication.getUserId(), sessionId));

        SessionResources response = new SessionResources(session);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        StandardSuccessResponse.<SessionResources>builder()
                                .data(response)
                                .message("Session started successfully")
                                .status(HttpStatus.OK.value())
                                .build()
                );    }
}
