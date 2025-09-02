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

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/technologies")
@AllArgsConstructor
@Tag(name = "Admin - Technology")
@Validated
public class GetTechnologiesByNameController {
    private final GetTechnologiesByNameUseCase getTechnologiesByNameUseCase;

    @Operation(summary = "Get technologies by names", description = "Return technologies that match the provided names (use query parameter `names`).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Technologies returned",
                    content = @Content(schema = @Schema(implementation = TechnologyResources.class))),
            @ApiResponse(responseCode = "404", description = "One or more Technologies not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request (e.g. empty names)", content = @Content)
    })
    @GetMapping("/name")
    public ResponseEntity<?> getTechnologies(
            @RequestParam(name = "names") @NotEmpty @Size(min = 1) List<@NotBlank String> names) {


            List<Technology> technologies = getTechnologiesByNameUseCase.execute(
                    new GetTechnologiesByNameCommand(names));

            List<TechnologyResources> responseDataList = technologies.stream()
                    .map(TechnologyResources::new
                    )
                    .toList();

            return ResponseEntity.status(HttpStatus.OK).body(
                    StandardSuccessResponse.<List<TechnologyResources>>builder()
                            .data(responseDataList)
                            .message("Technology returned successfully")
                            .status(HttpStatus.OK.value())
                            .build()
            );

    }
}

