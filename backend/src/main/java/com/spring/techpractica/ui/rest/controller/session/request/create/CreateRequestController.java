package com.spring.techpractica.ui.rest.controller.session.request.create;

import com.spring.techpractica.application.session.request.create.CreateRequestCommand;
import com.spring.techpractica.application.session.request.create.CreateRequestUseCase;
import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.core.user.UserAuthentication;
import com.spring.techpractica.ui.rest.resources.request.RequestResources;
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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions/requirements")
@AllArgsConstructor
@Tag(name = "Session", description = "Operations related to session requirements and requests")
public class CreateRequestController {

    private final CreateRequestUseCase useCase;

    @Operation(
            summary = "Create a new request for a requirement",
            description = "Creates a new request under a specific requirement. Requires authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Request created successfully",
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
    @PostMapping("/{requirementId}/requests")
    public ResponseEntity<StandardSuccessResponse<RequestResources>> invoke(
            @PathVariable UUID requirementId,
            @AuthenticationPrincipal UserAuthentication userAuthentication,
            @RequestBody CreateRequestDto createRequestDto) {

        Request request = useCase.execute(
                new CreateRequestCommand(
                        userAuthentication.getUserId(),
                        requirementId,
                        createRequestDto.getBrief()
                )
        );

        StandardSuccessResponse<RequestResources> response = StandardSuccessResponse.<RequestResources>builder()
                .data(new RequestResources(request))
                .message("Request created successfully")
                .status(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
