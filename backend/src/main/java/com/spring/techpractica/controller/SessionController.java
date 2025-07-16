package com.spring.techpractica.controller;

import com.spring.techpractica.dto.UserRequestSession;
import com.spring.techpractica.dto.session.SessionRequest;
import com.spring.techpractica.dto.session.SessionRequestCreation;
import com.spring.techpractica.dto.session.SessionResponse;
import com.spring.techpractica.dto.session.SessionsResponse;
import com.spring.techpractica.service.session.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/sessions")
@Tag(
        name = "Sessions Controller",
        description = "Handles user Session operations including create Session ....."
)
public class SessionController {


    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Operation(
            summary = "Create a new Session",
            description = "Allows a user to create a new Session by providing required session details like name, description, and many data fielis"
    )
    @PostMapping("/")
    public ResponseEntity<SessionResponse> createSession(
            @RequestBody SessionRequest sessionRequest
            , @AuthenticationPrincipal UserDetails userDetails) {

        String userEmail = userDetails.getUsername();

        return ResponseEntity.ok(sessionService.createSession(sessionRequest,
                userEmail));
    }


    @Operation(
            summary = "Get Sessions by system",
            description = "Fetches a paginated list of sessions based on the provided system name."
    )
    @GetMapping("/system")
    public ResponseEntity<SessionsResponse> getSessionsBySystemName(
            @RequestParam String systemName,
            @RequestParam int pageSize,
            @RequestParam int pageNumber) {
        return ResponseEntity.ok(sessionService.
                getSessionsBySystemName(systemName, pageSize, pageNumber));
    }

    @Operation(
            summary = "Get User Sessions ",
            description = "Retrieves a paginated list of the user sessions using page size and page number."
    )
    @GetMapping("/users")
    public ResponseEntity<SessionsResponse> getUserSessions(@AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestParam int pageSize,
                                                            @RequestParam int pageNumber) {
        String userEmail = userDetails.getUsername();
        return ResponseEntity.ok(sessionService.
                getUserSessions(userEmail, pageSize, pageNumber));


    }

    @Operation(
            summary = "Edit a Session",
            description = "Updates the session details for the given session ID. Requires the user's authentication."
    )
    @PutMapping("/{sessionId}")
    public ResponseEntity<SessionResponse> editSession(
            @PathVariable Long sessionId,
            @RequestBody SessionRequest updatedSessionRequest,
            @AuthenticationPrincipal UserDetails userDetails) {

        String userEmail = userDetails.getUsername();

        return ResponseEntity.ok(sessionService.
                updateSession(sessionId, updatedSessionRequest, userEmail));

    }


    @Operation(
            summary = "Delete a Session",
            description = "Deletes the session identified by the given session ID. Requires user authentication."
    )

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<String> deleteSession(
            @PathVariable Long sessionId,
            @AuthenticationPrincipal UserDetails userDetails) {

        String userEmail = userDetails.getUsername();
        sessionService.deleteSessionByUserEmailAndSessionId(userEmail, sessionId);
        return ResponseEntity.ok("Deleted Successfully");
    }

    /**
     * Retrieves a paginated list of sessions.
     *
     * @param pageSize   the number of sessions per page
     * @param pageNumber the current page number
     * @return paginated list of sessions wrapped in SessionsResponse
     */
    @Operation(
            summary = "Get paginated sessions",
            description = "Returns a list of sessions based on page size and number."
    )
    @GetMapping("/")
    public ResponseEntity<SessionsResponse> getSessions(
            @Parameter(description = "Number of sessions per page", example = "10")
            @RequestParam int pageSize,

            @Parameter(description = "Page number to retrieve", example = "1")
            @RequestParam int pageNumber) {
        return ResponseEntity.ok(sessionService.getSessions(pageSize, pageNumber));
    }

    @Operation(
            summary = "Get sessions matching user's skills",
            description = "Returns sessions that match the authenticated user's skills, with pagination support."
    )
    @GetMapping("/user/skills")
    public ResponseEntity<SessionsResponse>
    getSessionsByUserSkills(@AuthenticationPrincipal UserDetails userDetails,
                            @Parameter(description = "Number of sessions per page", example = "10")
                            @RequestParam int pageSize,

                            @Parameter(description = "Page number to retrieve", example = "1")
                            @RequestParam int pageNumber) {

        SessionsResponse sessionsResponse =
                sessionService.getSessionsByUserEmail(userDetails.getUsername(),
                        pageSize, pageNumber);

        return ResponseEntity.ok(sessionsResponse);
    }

    @PutMapping("/request")
    @Operation(summary = "Send a session request", description = "Allows a user to send a session request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<String> userRequestSession(@AuthenticationPrincipal UserDetails userDetails,
                                                     @RequestBody SessionRequestCreation sessionRequestCreation) {
        String userEmail = userDetails.getUsername();
        sessionService.createRequestSession(sessionRequestCreation, userEmail);
        return ResponseEntity.ok("send request successfully to session ");
    }

    @GetMapping("{sessionId}/request")
    @Operation(summary = "Get session requests", description = "Retrieves all session requests for a given session ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved session requests"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Session not found")
    })
    public ResponseEntity<List<UserRequestSession>> userRequestSession(
            @PathVariable Long sessionId,
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(sessionService.getSessionsRequest(sessionId, userDetails.getUsername()));
    }
}



