package com.spring.techpractica.UI.Rest.Controller.Session.GetSessions.UserSessions;

import com.spring.techpractica.Application.Session.GetSessions.UserSessions.GetUserSessionsUseCase;
import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.User.UserAuthentication;
import com.spring.techpractica.UI.Rest.Resources.Session.SessionCollection;
import com.spring.techpractica.UI.Rest.Shared.StandardSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Get user sessions",
            description = "Retrieves all sessions that belong to the currently authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User sessions retrieved successfully",
                    content = @Content(schema = @Schema(implementation = SessionCollection.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No sessions found for the user",
                    content = @Content)
    })
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