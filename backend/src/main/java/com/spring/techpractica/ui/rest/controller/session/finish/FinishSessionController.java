package com.spring.techpractica.ui.rest.controller.session.finish;

import com.spring.techpractica.application.session.finish.FinishSessionCommand;
import com.spring.techpractica.application.session.finish.FinishSessionUseCase;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.user.UserAuthentication;
import com.spring.techpractica.ui.rest.resources.session.SessionResources;
import com.spring.techpractica.ui.rest.shared.StandardSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "Sessions")
public class FinishSessionController {

    private final FinishSessionUseCase finishSessionUseCase;

    @PutMapping("/{sessionId}/finish")
    @Operation(
            summary = "Finish session",
            description = "Marks a session as finished. Only the session owner is allowed to perform this action."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Session finished successfully",
            content = @Content(schema = @Schema(implementation = SessionResources.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Session not found"
    )
    @ApiResponse(
            responseCode = "403",
            description = "User not authorized to finish this session"
    )
    public ResponseEntity<?> finishSession(
            @AuthenticationPrincipal
            @Parameter(hidden = true)
            UserAuthentication user,

            @PathVariable
            @Parameter(
                    description = "Session unique identifier",
                    required = true
            )
            UUID sessionId
    ) {
        Session session = finishSessionUseCase.execute(
                new FinishSessionCommand(
                        user.getUserId(),
                        sessionId
                )
        );

        SessionResources responseData = new SessionResources(session);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        StandardSuccessResponse.<SessionResources>builder()
                                .data(responseData)
                                .message("Session finished successfully")
                                .status(HttpStatus.OK.value())
                                .build()
                );
    }
}
