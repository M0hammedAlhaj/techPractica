package com.spring.techpractica.ui.rest.controller.admin.system.create;

import com.spring.techpractica.application.admin.system.create.CreateSystemCommand;
import com.spring.techpractica.application.admin.system.create.CreateSystemUseCase;
import com.spring.techpractica.core.system.entity.System;
import com.spring.techpractica.ui.rest.resources.system.SystemResources;
import com.spring.techpractica.ui.rest.shared.StandardSuccessResponse;
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

@RestController
@RequestMapping("/api/v1/admin/systems")
@AllArgsConstructor
@Tag(name = "Admin - System")
public class CreateSystemController {
    private final CreateSystemUseCase createSystemUseCase;


    @Operation(summary = "Create new System", description = "Admin creates a new System and returns the created resource.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "System created successfully",
                    content = @Content(schema = @Schema(implementation = SystemResources.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized (invalid credentials)", content = @Content),
            @ApiResponse(responseCode = "409", description = "System with the same name already exists", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<?> createSystem(@RequestBody @Valid CreateSystemRequest request) {


        System system = createSystemUseCase.execute(new CreateSystemCommand(request.name()));

        SystemResources responseData = new SystemResources(system);
        return ResponseEntity.ok(StandardSuccessResponse.<SystemResources>builder()
                .data(responseData)
                .message("Created System successfully")
                .status(HttpStatus.CREATED.value())
                .build());

    }
}
