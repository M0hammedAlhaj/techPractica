package com.spring.techpractica.ui.rest.controller.session.get.by.specifications;

import com.spring.techpractica.application.session.get.by.specifications.GetSessionsBySpecificationsCommand;
import com.spring.techpractica.application.session.get.by.specifications.GetSessionsBySpecificationsUseCase;
import com.spring.techpractica.application.session.get.sessions.count.GetSessionsCountUseCase;
import com.spring.techpractica.ui.rest.resources.session.SessionCollection;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sessions")
@AllArgsConstructor
@Tag(name = "Session")
public class GetSessionsBySpecificationsController {

    private final GetSessionsBySpecificationsUseCase useCase;

    private final GetSessionsCountUseCase getSessionsCountUseCase;

    @Operation(
            summary = "Get sessions by specification",
            description = "Retrieves a paginated list of sessions filtered by optional field and session names with optional sorting."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sessions retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StandardSuccessResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid page, size, or sort parameters", content = @Content),
            @ApiResponse(responseCode = "404", description = "No sessions found for the given filters", content = @Content),
            @ApiResponse(responseCode = "501", description = "Operation not supported", content = @Content)
    })

    @GetMapping("/spec")
    public ResponseEntity<?> getSessionsBySpecification(
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String fieldName,
            @RequestParam(required = false) String sessionName,
            @RequestParam(required = false) String sessionCode,
            @RequestParam(required = false) String systemName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        var response = new SessionCollection(useCase.execute(
                new GetSessionsBySpecificationsCommand(
                        sort,
                        fieldName,
                        sessionName,
                        sessionCode,
                        systemName,
                        page,
                        size)),
                getSessionsCountUseCase.execute()
        );

        return ResponseEntity.ok(StandardSuccessResponse.<SessionCollection>builder()
                .data(response)
                .message("Get sessions by specification successfully executed")
                .status(HttpStatus.OK.value())
                .build());
    }
}

