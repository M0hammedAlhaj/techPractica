package com.spring.techpractica.UI.Rest.Controller.Admin.System.GetSystem.GetAllSystems;

import com.spring.techpractica.Application.Admin.System.GetSystem.GetAllSystems.GetAllSystemsCommand;
import com.spring.techpractica.Application.Admin.System.GetSystem.GetAllSystems.GetAllSystemsUseCase;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.System.Entity.System;
import com.spring.techpractica.UI.Rest.Resources.System.SystemResources;
import com.spring.techpractica.UI.Rest.Shared.StandardErrorResponse;
import com.spring.techpractica.UI.Rest.Shared.StandardSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/systems")
@AllArgsConstructor
@Tag(name = "Admin - System")
@Validated
public class GetAllSystemsController {
    private final GetAllSystemsUseCase getAllSystemsUseCase;

    @Operation(summary = "Create new Technology", description = "Admin creates a new Technology and optionally links existing Fields")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Technology created",
                    content = @Content(schema = @Schema(implementation = SystemResources.class))),
            @ApiResponse(responseCode = "409", description = "Technology name already exists", content = @Content),
            @ApiResponse(responseCode = "404", description = "One or more Fields not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content)
    })
    @GetMapping("/")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSystems(@RequestBody @Validated GetAllSystemsRequest request) {
        try {
            List<System> systems = getAllSystemsUseCase.execute(new GetAllSystemsCommand());

            List<SystemResources> responseDataList = systems.stream()
                    .map(system -> SystemResources.builder()
                            .id(system.getId())
                            .name(system.getName())
                            .build())
                    .toList();

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    StandardSuccessResponse.builder()
                            .data(responseDataList)
                            .message("Systems returned successfully")
                            .status(HttpStatus.OK.value())
                            .build()
            );
        } catch (ResourcesNotFoundException ex) {
            StandardErrorResponse response = StandardErrorResponse.builder()
                    .timestamp(Instant.now())
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(ex.getMessage())
                    .code("FIELD_NOT_FOUND")
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
