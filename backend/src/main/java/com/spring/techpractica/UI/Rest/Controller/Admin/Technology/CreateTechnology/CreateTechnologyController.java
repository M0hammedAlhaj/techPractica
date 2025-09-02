package com.spring.techpractica.UI.Rest.Controller.Admin.Technology.CreateTechnology;

import com.spring.techpractica.Application.Admin.Technology.CreateTechnology.CreateTechnologyCommand;
import com.spring.techpractica.Application.Admin.Technology.CreateTechnology.CreateTechnologyUseCase;
import com.spring.techpractica.Core.Shared.Exception.ResourcesDuplicateException;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import com.spring.techpractica.UI.Rest.Resources.Field.FieldCollection;
import com.spring.techpractica.UI.Rest.Resources.Technology.TechnologyResources;
import com.spring.techpractica.UI.Rest.Shared.StandardErrorResponse;
import com.spring.techpractica.UI.Rest.Shared.StandardSuccessResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/admin/technologies")
@AllArgsConstructor
@Tag(name = "Admin - Technology")
@Validated
public class CreateTechnologyController {
    private final CreateTechnologyUseCase createTechnologyUseCase;

    @Operation(summary = "Create new Technology", description = "Admin creates a new Technology and optionally links existing Fields.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Technology created",
                    content = @Content(schema = @Schema(implementation = TechnologyResources.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized (invalid credentials)", content = @Content),
            @ApiResponse(responseCode = "409", description = "Technology name already exists", content = @Content),
            @ApiResponse(responseCode = "404", description = "One or more Fields not found", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<?> createTechnology(@RequestBody @Validated CreateTechnologyRequest request) {

            Technology technology = createTechnologyUseCase.execute(
                    new CreateTechnologyCommand(request.name(), request.fieldNames()));

            TechnologyResources responseData = new TechnologyResources(technology);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    StandardSuccessResponse.<TechnologyResources>builder()
                            .data(responseData)
                            .message("Technology created successfully")
                            .status(HttpStatus.CREATED.value())
                            .build()
            );

    }
}
