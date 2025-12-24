package com.spring.techpractica.ui.rest.controller.session.delete.participant;

import com.spring.techpractica.application.session.delete.participant.DeleteSessionParticipantCommand;
import com.spring.techpractica.application.session.delete.participant.SessionParticipantUseCase;
import com.spring.techpractica.core.user.UserAuthentication;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.UUID;

@RestController
@RequestMapping("/api/v1/session/")
@AllArgsConstructor
@Tag(name = "Session")
public class SessionParticipantController {

    private final SessionParticipantUseCase sessionParticipantUseCase;

    @Operation(
            summary = "Remove participant from session",
            description = "Allows the session owner to remove a participant from a session"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Participant removed successfully"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User is not the session owner or attempted to remove the owner"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Session or participant not found"
            )
    })

    @DeleteMapping("/{requestId}/{sessionId}/participants/{participantId}")
    public ResponseEntity<?> deleteSessionParticipant(@AuthenticationPrincipal UserAuthentication  user,
                                                      @PathVariable UUID requestId,
                                                      @PathVariable UUID sessionId,
                                                      @PathVariable UUID participantId) {
        UUID ownerId = user.getUserId();

        sessionParticipantUseCase.execute(new DeleteSessionParticipantCommand(ownerId, participantId, sessionId, requestId));

        return ResponseEntity.ok("Delete Session participant executed successfully")
                .status(HttpStatus.OK)
                .build();
    }
}