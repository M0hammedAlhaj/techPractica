package com.spring.techpractica.ui.rest.controller.admin.system.update;

import com.spring.techpractica.application.admin.system.update.UpdateSystemCommand;
import com.spring.techpractica.application.admin.system.update.UpdateSystemUseCase;
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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/systems")
@AllArgsConstructor
@Tag(name = "Admin - System")
public class UpdateSystemController {

    private final UpdateSystemUseCase updateSystemUseCase;

    @Operation(summary = "Update System", description = "Admin update a System and returns the updated resource.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "System updated successfully",
                    content = @Content(schema = @Schema(implementation = SystemResources.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized (invalid credentials)", content = @Content),
            @ApiResponse(responseCode = "409", description = "Field with the same name already exists", content = @Content)
    })
    @PutMapping("/{systemId}/")
    public ResponseEntity<?> updateSystem(@RequestBody @Valid UpdateSystemRequest request, @PathVariable UUID systemId) {

        System system = updateSystemUseCase.execute(new UpdateSystemCommand(systemId,request.name()));

        SystemResources responseData = new SystemResources(system);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                StandardSuccessResponse.<SystemResources>builder()
                        .data(responseData)
                        .message("Field updated successfully")
                        .status(HttpStatus.CREATED.value())
                        .build()
        );
    }
}

