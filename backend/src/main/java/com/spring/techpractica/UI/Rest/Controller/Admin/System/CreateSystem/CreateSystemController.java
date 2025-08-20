package com.spring.techpractica.UI.Rest.Controller.Admin.System.CreateSystem;

import com.spring.techpractica.Application.Admin.System.CreateSystem.CreateSystemCommand;
import com.spring.techpractica.Application.Admin.System.CreateSystem.CreateSystemUseCase;
import com.spring.techpractica.Core.Shared.Exception.ResourcesDuplicateException;
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
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.Instant;

@RestController
@RequestMapping("/api/v1/admin/systems")
@AllArgsConstructor
@Tag(name = "Admin - System")
public class CreateSystemController {
    private final CreateSystemUseCase createSystemUseCase;

    @Operation(summary = "Admin create system", description = "Admin create system and returns the data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created System successfully",
                    content = @Content(schema = @Schema(implementation = SystemResources.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request payload",
                    content = @Content),

    })
    @PostMapping("/")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createSystem(@RequestBody @Valid CreateSystemRequest request) {
        try {
            System system = createSystemUseCase.execute(new CreateSystemCommand(request.name()));

            SystemResources responseData = SystemResources.builder()
                    .id(system.getId())
                    .name(system.getName())
                    .build();
            return ResponseEntity.ok(StandardSuccessResponse.<SystemResources>builder()
                    .data(responseData)
                    .message("Created System successfully")
                    .status(HttpStatus.CREATED.value())
                    .build());
        } catch (ResourcesDuplicateException ex) {
            StandardErrorResponse response = StandardErrorResponse.builder()
                    .timestamp(Instant.now())
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message(ex.getMessage())
                    .code("EXISTS_SYSTEM")
                    .build();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
