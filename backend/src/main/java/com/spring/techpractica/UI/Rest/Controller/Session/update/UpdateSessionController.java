package com.spring.techpractica.UI.Rest.Controller.Session.update;

import com.spring.techpractica.Application.Session.update.UpdateSessionCommand;
import com.spring.techpractica.Application.Session.update.UpdateSessionUseCase;
import com.spring.techpractica.Core.Requirement.Model.RequirementRequest;
import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.User.UserAuthentication;
import com.spring.techpractica.UI.Rest.Controller.Session.create.Request.CreateSessionRequest;
import com.spring.techpractica.UI.Rest.Resources.Session.SessionResources;
import com.spring.techpractica.UI.Rest.Shared.StandardSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
@AllArgsConstructor
public class UpdateSessionController {

    private final UpdateSessionUseCase updateSessionUseCase;

    @Operation(
            summary = "Update existing Session",
            description = "Updates an existing session for the authenticated user. "
                    + "The user must be the session owner. Returns the updated session resource."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Session updated successfully",
                    content = @Content(schema = @Schema(implementation = StandardSuccessResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload",
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
                    description = "Session or related resource not found",
                    content = @Content
            )
    })
    @PostMapping("/{sessionId}")
    public ResponseEntity<?> updateSession(@RequestBody @Valid CreateSessionRequest request,
                                           @AuthenticationPrincipal UserAuthentication userAuthentication,
                                           @PathVariable UUID sessionId) {

        Session session = updateSessionUseCase.execute(new UpdateSessionCommand(
                userAuthentication.getUserId(),
                sessionId,
                request.name(),
                request.description(),
                request.isPrivate(),
                request.system(),
                request.requirements().stream().map(
                        requirementRequest -> new RequirementRequest(requirementRequest.getField()
                                , requirementRequest.getTechnologies())
                ).toList()
        ));

        SessionResources responseData = new SessionResources(session);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        StandardSuccessResponse.<SessionResources>builder()
                                .data(responseData)
                                .message("Session updated successfully")
                                .status(HttpStatus.OK.value())
                                .build()
                );
    }
}
