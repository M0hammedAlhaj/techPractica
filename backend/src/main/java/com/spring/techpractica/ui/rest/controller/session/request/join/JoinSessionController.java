package com.spring.techpractica.ui.rest.controller.session.request.join;


import com.spring.techpractica.application.session.request.join.JoinSessionCommand;
import com.spring.techpractica.application.session.request.join.JoinSessionUseCase;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.user.UserAuthentication;
import com.spring.techpractica.ui.rest.resources.request.Request.RequestResources;
import com.spring.techpractica.ui.rest.resources.session.SessionResources;
import com.spring.techpractica.ui.rest.shared.StandardErrorResponse;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions/joins")
@AllArgsConstructor
@Tag(name = "Session", description = "Operations related to session requirements and requests")
public class JoinSessionController {

    private final JoinSessionUseCase joinSessionUseCase;

    @Operation(
            summary = "approve requests for a specific session",
            description = "approve the requests for a specific session. Requires authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Request approved successfully",
                    content = @Content(schema = @Schema(implementation = RequestResources.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = StandardErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized access",
                    content = @Content(schema = @Schema(implementation = StandardErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Resources not found",
                    content = @Content(schema = @Schema(implementation = StandardErrorResponse.class))
            )
    })
    @PutMapping("/{sessionId}")
    public ResponseEntity<?> joinSession(@AuthenticationPrincipal UserAuthentication userAuthentication
            , @PathVariable("sessionId") UUID sessionId) {

        Session session = joinSessionUseCase.execute(new JoinSessionCommand(userAuthentication.getUserId(), sessionId));
        SessionResources responseData = new SessionResources(session);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        StandardSuccessResponse.<SessionResources>builder()
                                .data(responseData)
                                .message("Requests approved successfully")
                                .status(HttpStatus.OK.value())
                                .build()
                );
    }


}
