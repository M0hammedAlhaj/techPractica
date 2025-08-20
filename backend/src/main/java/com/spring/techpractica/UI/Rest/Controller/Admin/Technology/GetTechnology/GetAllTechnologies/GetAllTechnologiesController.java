package com.spring.techpractica.UI.Rest.Controller.Admin.Technology.GetTechnology.GetAllTechnologies;

import com.spring.techpractica.Application.Admin.Technology.GetTechnology.GetAllTechnologies.GetAllTechnologiesCommand;
import com.spring.techpractica.Application.Admin.Technology.GetTechnology.GetAllTechnologies.GetAllTechnologiesUseCase;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import com.spring.techpractica.UI.Rest.Resources.Technology.TechnologyCollection;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/technologies")
@AllArgsConstructor
@Tag(name = "Admin - Technology")
@Validated
public class GetAllTechnologiesController {
    private final GetAllTechnologiesUseCase getAllTechnologiesUseCase;

    @Operation(summary = "Create new Technology", description = "Admin creates a new Technology and optionally links existing Fields")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Technology created",
                    content = @Content(schema = @Schema(implementation = TechnologyCollection.class))),
            @ApiResponse(responseCode = "409", description = "Technology name already exists", content = @Content),
            @ApiResponse(responseCode = "404", description = "One or more Fields not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content)
    })
    @GetMapping("/")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getTechnologies(@RequestBody @Validated GetAllTechnologiesRequest request) {
        try {
            List<Technology> technologies = getAllTechnologiesUseCase.execute(new GetAllTechnologiesCommand());

            TechnologyCollection responseDataList = new TechnologyCollection(technologies);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    StandardSuccessResponse.<TechnologyCollection>builder()
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
