package com.spring.techpractica.UI.Rest.Controller.Admin.Technology.GetTechnology.GetTechnologiesByName;

import com.spring.techpractica.Application.Admin.Technology.GetTechnology.GetTechnologiesByName.GetTechnologiesByNameCommand;
import com.spring.techpractica.Application.Admin.Technology.GetTechnology.GetTechnologiesByName.GetTechnologiesByNameUseCase;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import com.spring.techpractica.UI.Rest.Resources.Field.FieldCollection;
import com.spring.techpractica.UI.Rest.Resources.Technology.TechnologyResources;
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
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/technologies")
@AllArgsConstructor
@Tag(name = "Admin - Technology")
@Validated
public class GetTechnologiesByNameController {
    private final GetTechnologiesByNameUseCase getTechnologiesByNameUseCase;

    @Operation(summary = "Create new Technology", description = "Admin creates a new Technology and optionally links existing Fields")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Technology created",
                    content = @Content(schema = @Schema(implementation = TechnologyResources.class))),
            @ApiResponse(responseCode = "409", description = "Technology name already exists", content = @Content),
            @ApiResponse(responseCode = "404", description = "One or more Fields not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content)
    })
    @GetMapping("/name")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getTechnologies(@RequestBody @Validated GetTechnologiesByNameRequest request) {
        try {
            List<Technology> technologies = getTechnologiesByNameUseCase.execute(
                    new GetTechnologiesByNameCommand(request.names()));

            List<TechnologyResources> responseDataList = technologies.stream()
                    .map(technology -> TechnologyResources.builder()
                            .id(technology.getId())
                            .name(technology.getName())
                                    .fields(new FieldCollection(technology.getFields()))
                                    .build()
                                )
                    .toList();

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    StandardSuccessResponse.<List<TechnologyResources>>builder()
                            .data(responseDataList)
                            .message("Technology created successfully")
                            .status(HttpStatus.CREATED.value())
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
