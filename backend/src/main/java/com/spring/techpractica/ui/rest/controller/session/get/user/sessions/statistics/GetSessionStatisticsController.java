package com.spring.techpractica.ui.rest.controller.session.get.user.sessions.statistics;

import com.spring.techpractica.application.session.get.sessions.statistics.GetSessionStatisticsCommand;
import com.spring.techpractica.application.session.get.sessions.statistics.GetSessionStatisticsUseCase;
import com.spring.techpractica.core.session.entity.statistics.SessionStatistics;
import com.spring.techpractica.core.user.UserAuthentication;
import com.spring.techpractica.ui.rest.resources.session.statistics.SessionStatisticsResources;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
@AllArgsConstructor
@Tag(name = "Session")
public class GetSessionStatisticsController {

    private final GetSessionStatisticsUseCase getSessionStatisticsUseCase;

    @Operation(
            summary = "Get session statistics",
            description = "Returns aggregated statistics for a specific session including total tasks, duration, and per–user statistics."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Session statistics retrieved successfully",
                    content = @Content(schema = @Schema(implementation = SessionStatisticsResources.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized – user is not authenticated"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden – user is not a member of the session"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Session not found"
            )
    })
    @GetMapping("/statistics/{sessionId}")
    public ResponseEntity<?> getSessionStatistics(@AuthenticationPrincipal UserAuthentication user,
                                                  @PathVariable UUID sessionId) {
        SessionStatistics statistics = getSessionStatisticsUseCase.execute(
                new GetSessionStatisticsCommand(
                        user.getUserId(),
                        sessionId
                )
        );

        SessionStatisticsResources response = new SessionStatisticsResources(statistics);

        return ResponseEntity.ok(StandardSuccessResponse.<SessionStatisticsResources>builder()
                .data(response)
                .message("Get session statistics successfully executed")
                .status(HttpStatus.OK.value())
                .build());
    }
}