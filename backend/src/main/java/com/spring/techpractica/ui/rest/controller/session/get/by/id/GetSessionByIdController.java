package com.spring.techpractica.ui.rest.controller.session.get.by.id;

import com.spring.techpractica.application.session.get.by.id.GetSessionByIdUseCase;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.user.UserAuthentication;
import com.spring.techpractica.ui.rest.resources.session.SessionResources;
import com.spring.techpractica.ui.rest.shared.StandardSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
@AllArgsConstructor
@Tag(name = "Session")
public class GetSessionByIdController {

    private final GetSessionByIdUseCase  getSessionByIdUseCase;

    @Operation(
            summary = "Get session by ID",
            description = "Retrieves a session using its unique session ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session retrieved successfully",
                    content = @Content(schema = @Schema(implementation = SessionResources.class))),
            @ApiResponse(responseCode = "404", description = "Session not found",
                    content = @Content)
    })
    @GetMapping("/by-id/{sessionId}")
    public ResponseEntity<?> getSessionById(@PathVariable UUID sessionId,
                                            @AuthenticationPrincipal UserAuthentication userAuthentication) {

        Session session = getSessionByIdUseCase.execute(sessionId);

        SessionResources responseData = new SessionResources(session);

        return ResponseEntity.ok(StandardSuccessResponse.<SessionResources>builder()
                .data(responseData)
                .message("Get sessions by ID successfully executed")
                .status(HttpStatus.OK.value())
                .build());    }
}