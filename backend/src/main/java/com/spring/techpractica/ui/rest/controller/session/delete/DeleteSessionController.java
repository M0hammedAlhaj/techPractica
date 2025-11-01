package com.spring.techpractica.ui.rest.controller.session.delete;

import com.spring.techpractica.application.session.delete.DeleteSessionCommand;
import com.spring.techpractica.application.session.delete.DeleteSessionUseCase;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.user.UserAuthentication;
import com.spring.techpractica.ui.rest.resources.session.SessionResources;
import com.spring.techpractica.ui.rest.shared.StandardSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
@AllArgsConstructor
public class DeleteSessionController {

    private final DeleteSessionUseCase deleteSessionUseCase;

    @Operation(
            summary = "Delete existing Session",
            description = "Deletes an existing session for the authenticated user. "
                    + "The user must be the session owner. Returns the deleted session resource (after deletion)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Session deleted successfully",
                    content = @Content(schema = @Schema(implementation = StandardSuccessResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request (e.g. malformed UUID)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized (invalid credentials)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden (user is not the owner of the session)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Session not found",
                    content = @Content
            )
    })
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<?> deleteSession(
            @AuthenticationPrincipal UserAuthentication userAuthentication,
            @PathVariable UUID sessionId) {

        Session session = deleteSessionUseCase.execute(new DeleteSessionCommand(
                userAuthentication.getUserId(),
                sessionId));

        SessionResources responseData = new SessionResources(session);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        StandardSuccessResponse.<SessionResources>builder()
                                .data(responseData)
                                .message("Session deleted successfully")
                                .status(HttpStatus.OK.value())
                                .build()
                );
    }
}
