package com.spring.techpractica.ui.rest.controller.session.request.reject;

import com.spring.techpractica.application.session.request.reject.RejectSessionsRequestsCommand;
import com.spring.techpractica.application.session.request.reject.RejectSessionsRequestsUseCase;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions/requests")
@AllArgsConstructor
@Tag(name = "Session", description = "Operations related to session requirements and requests")
public class RejectSessionsRequestsController {

    private final RejectSessionsRequestsUseCase rejectSessionsRequestsUseCase;

    @Operation(
            summary = "Reject requests for a specific session",
            description = "Reject the requests for a specific session. Requires authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Request rejected successfully",
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
    @PutMapping("/reject/{sessionId}/{requestId}")
    public ResponseEntity<?> rejectSessionRequests(@AuthenticationPrincipal UserAuthentication userAuthentication
            , @PathVariable("sessionId") UUID sessionId, @PathVariable("requestId") UUID requestId) {

        Request request = rejectSessionsRequestsUseCase.execute(
                new RejectSessionsRequestsCommand(
                        userAuthentication.getUserId(),
                        sessionId,
                        requestId
                )
        );


        RequestResources responseData = new RequestResources(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        StandardSuccessResponse.<RequestResources>builder()
                                .data(responseData)
                                .message("Request rejected successfully")
                                .status(HttpStatus.OK.value())
                                .build()
                );
    }
}
