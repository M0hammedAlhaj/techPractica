package com.spring.techpractica.ui.rest.controller.session.get.user.sessions;

import com.spring.techpractica.application.session.get.user.sessions.count.GetUserSessionsCountCommand;
import com.spring.techpractica.application.session.get.user.sessions.count.GetUserSessionsCountUseCase;
import com.spring.techpractica.application.session.get.user.sessions.GetUserSessionCommand;
import com.spring.techpractica.application.session.get.user.sessions.GetUserSessionsUseCase;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.user.UserAuthentication;
import com.spring.techpractica.ui.rest.resources.session.SessionCollection;
import com.spring.techpractica.ui.rest.resources.session.UserSessionCollection;
import com.spring.techpractica.ui.rest.shared.exception.InvalidPageRequestException;
import com.spring.techpractica.ui.rest.shared.StandardSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
@AllArgsConstructor
public class GetUserSessionsController {

    private final GetUserSessionsUseCase getUserSessionsUseCase;

    private final GetUserSessionsCountUseCase getUserSessionsCountUseCase;

    @Operation(
            summary = "Get user sessions",
            description = "Retrieves all sessions that belong to the currently authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User sessions retrieved successfully",
                    content = @Content(schema = @Schema(implementation = UserSessionCollection.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No sessions found for the user",
                    content = @Content)
    })
    @GetMapping("/by-user")
    public ResponseEntity<?> getSessionsByUser(@AuthenticationPrincipal UserAuthentication userAuthentication,
                                               @RequestParam int size,
                                               @RequestParam int page) {
        if (page < 0 || size < 1) {
            throw new InvalidPageRequestException(page, size);
        }

        UUID userId = userAuthentication.getUserId();

        Page<Session> userSessions = getUserSessionsUseCase.execute(new GetUserSessionCommand(userId, size, page));

        UserSessionCollection sessionCollection = new UserSessionCollection(
                userSessions.getContent(),
                getUserSessionsCountUseCase.execute(new GetUserSessionsCountCommand(userId))
                        ,userId
        );

        return ResponseEntity.ok(StandardSuccessResponse.<UserSessionCollection>builder()
                .data(sessionCollection)
                .message("Get User Sessions Successfully executed")
                .status(HttpStatus.OK.value())
                .build());
    }
}