package com.spring.techpractica.ui.rest.controller.session.request.get;

import com.spring.techpractica.application.session.get.user.sessions.count.GetUserSessionsCountUseCase;
import com.spring.techpractica.application.session.request.get.sessions.requests.GetSessionsRequestsCommand;
import com.spring.techpractica.application.session.request.get.sessions.requests.GetSessionsRequestsUseCase;
import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.core.user.UserAuthentication;
import com.spring.techpractica.ui.rest.resources.request.FullRequest.FullRequestCollection;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions/requests")
@AllArgsConstructor
@Tag(name = "Session", description = "Operations related to session requirements and requests")
public class GetSessionsRequestsController {

    private final GetSessionsRequestsUseCase getSessionsRequestsUseCase;
    private final GetUserSessionsCountUseCase getUserSessionsCountUseCase;

    @Operation(
            summary = "return requests for a specific session",
            description = "retuern the requests for a specific session. Requires authentication."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Request returned successfully",
                    content = @Content(schema = @Schema(implementation = FullRequestCollection.class))
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
    @GetMapping("/{sessionId}/")
    public ResponseEntity<?> getSessionRequests(@AuthenticationPrincipal UserAuthentication userAuthentication
            , @PathVariable("sessionId") UUID sessionId) {

        List<Request> requests = getSessionsRequestsUseCase.execute(
                new GetSessionsRequestsCommand(
                        userAuthentication.getUserId(),
                        sessionId
                )
        );

        FullRequestCollection responseData = new FullRequestCollection(requests, getUserSessionsCountUseCase);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        StandardSuccessResponse.<FullRequestCollection>builder()
                                .data(responseData)
                                .message("Requests returned successfully")
                                .status(HttpStatus.OK.value())
                                .build()
                );
    }
}
