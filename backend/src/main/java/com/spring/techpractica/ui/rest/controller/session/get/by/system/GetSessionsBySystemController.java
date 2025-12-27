package com.spring.techpractica.ui.rest.controller.session.get.by.system;

import com.spring.techpractica.application.session.get.by.system.GetSessionsBySystemCommand;
import com.spring.techpractica.application.session.get.by.system.GetSessionsBySystemUseCase;
import com.spring.techpractica.application.session.get.sessions.count.GetSessionsCountUseCase;
import com.spring.techpractica.ui.rest.resources.session.SessionCollection;
import com.spring.techpractica.ui.rest.shared.exception.InvalidPageRequestException;
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

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
@AllArgsConstructor
@Tag(name = "Session")
public class GetSessionsBySystemController {

    private final GetSessionsBySystemUseCase getSessionsBySystemUseCase;


    private final GetSessionsCountUseCase getSessionsCountUseCase;

    @Operation(
            summary = "Get sessions by system",
            description = "Retrieves a paginated list of sessions filtered by" +
                          " a specific system name"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sessions retrieved successfully",
                    content = @Content(schema = @Schema(implementation = SessionCollection.class))),
            @ApiResponse(responseCode = "400", description = "Invalid page or size parameters",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "System not found",
                    content = @Content),
            @ApiResponse(responseCode = "501", description = "Operation not supported",
                    content = @Content)
    })
    @GetMapping(value = "/{systemId}")
    public ResponseEntity<?> getSessionsBySystem(@PathVariable UUID systemId,
                                                 @RequestParam int size,
                                                 @RequestParam int page) {
        if (page < 0 || size < 1) {
            throw new InvalidPageRequestException(page, size);
        }

        SessionCollection response = new SessionCollection(
                getSessionsBySystemUseCase.execute(
                        new GetSessionsBySystemCommand(systemId, page, size)),
                getSessionsCountUseCase.execute()
        );

        return ResponseEntity.ok(StandardSuccessResponse.<SessionCollection>builder()
                .data(response)
                .message("Get sessions by system successfully executed")
                .status(HttpStatus.OK.value())
                .build());
    }
}
