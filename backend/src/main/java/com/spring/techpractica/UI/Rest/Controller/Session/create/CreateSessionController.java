package com.spring.techpractica.UI.Rest.Controller.Session.create;

import com.spring.techpractica.Application.Session.create.CreateSessionCommand;
import com.spring.techpractica.Application.Session.create.CreateSessionUseCase;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sessions")
@AllArgsConstructor
@Tag(name = "Session")
public class CreateSessionController {

    private final CreateSessionUseCase createSessionUseCase;

    @Operation(
            summary = "Create new Session",
            description = "Creates a new Session for the authenticated user and returns the created resource."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Session created successfully",
                    content = @Content(schema = @Schema(implementation = StandardSuccessResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized (invalid credentials)", content = @Content),
    })
    @PostMapping("/")
    public ResponseEntity<?> createSession(@RequestBody @Valid CreateSessionRequest request,
                                           @AuthenticationPrincipal UserAuthentication userAuthentication) {
        Session session = createSessionUseCase.execute(new CreateSessionCommand(
                userAuthentication.getUserId(),
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

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        StandardSuccessResponse.<SessionResources>builder()
                                .data(responseData)
                                .message("Session created successfully")
                                .status(HttpStatus.CREATED.value())
                                .build()
                );
    }
}