package com.spring.techpractica.UI.Rest.Controller.Admin.Technology.UpdateTechnology;

import com.spring.techpractica.Application.Admin.Technology.CreateTechnology.CreateTechnologyCommand;
import com.spring.techpractica.Application.Admin.Technology.CreateTechnology.CreateTechnologyUseCase;
import com.spring.techpractica.Application.Admin.Technology.UpdateTechnology.UpdateTechnologyCommand;
import com.spring.techpractica.Application.Admin.Technology.UpdateTechnology.UpdateTechnologyUseCase;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import com.spring.techpractica.UI.Rest.Resources.Technology.TechnologyResources;
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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/technologies")
@AllArgsConstructor
@Tag(name = "Admin - Technology")
@Validated
public class UpdateTechnologyController {
    private final UpdateTechnologyUseCase updateTechnologyUseCase;

    @Operation(summary = "Update a Technology", description = "Admin updated a Technology and return it.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Technology updated",
                    content = @Content(schema = @Schema(implementation = TechnologyResources.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized (invalid credentials)", content = @Content),
            @ApiResponse(responseCode = "409", description = "Technology name already exists", content = @Content),
            @ApiResponse(responseCode = "404", description = "One or more Fields not found", content = @Content)
    })
    @PutMapping("/{technologyId}/")
    public ResponseEntity<?> updateTechnology(@PathVariable UUID technologyId, @RequestBody @Validated UpdateTechnologyRequest request) {

            Technology technology = updateTechnologyUseCase.execute(
                    new UpdateTechnologyCommand(technologyId,request.name(), request.fieldNames()));

            TechnologyResources responseData = new TechnologyResources(technology);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    StandardSuccessResponse.<TechnologyResources>builder()
                            .data(responseData)
                            .message("Technology updated successfully")
                            .status(HttpStatus.CREATED.value())
                            .build()
            );

    }
}
