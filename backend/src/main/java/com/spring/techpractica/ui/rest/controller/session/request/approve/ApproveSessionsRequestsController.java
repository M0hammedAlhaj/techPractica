package com.spring.techpractica.ui.rest.controller.session.request.approve;

import com.spring.techpractica.application.session.request.approve.ApproveSessionsRequestsCommand;
import com.spring.techpractica.application.session.request.approve.ApproveSessionsRequestsUseCase;
import com.spring.techpractica.application.session.request.RequestSessionResponse;
import com.spring.techpractica.core.user.UserAuthentication;
import com.spring.techpractica.ui.rest.resources.request.Request.ApproveSessionResources;
import com.spring.techpractica.ui.rest.resources.request.Request.RequestResources;
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
@RequestMapping("/api/v1/sessions/requests")
@AllArgsConstructor
@Tag(name = "Session", description = "Operations related to session requirements and requests")
public class ApproveSessionsRequestsController {

    private final ApproveSessionsRequestsUseCase approveSessionsRequestsUseCase;

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
    @PutMapping("/approve/{sessionId}/{requestId}")
    public ResponseEntity<?> ApproveSessionRequests(@AuthenticationPrincipal UserAuthentication userAuthentication
            , @PathVariable("sessionId") UUID sessionId, @PathVariable("requestId") UUID requestId) {

        RequestSessionResponse approvedRequest = approveSessionsRequestsUseCase.execute(
                new ApproveSessionsRequestsCommand(
                        userAuthentication.getUserId(),
                        sessionId,
                        requestId
                )
        );

        ApproveSessionResources responseData = new ApproveSessionResources(approvedRequest.getRequest(), approvedRequest.getNotification());

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        StandardSuccessResponse.<ApproveSessionResources>builder()
                                .data(responseData)
                                .message("Requests approved successfully")
                                .status(HttpStatus.OK.value())
                                .build()
                );
    }
}
