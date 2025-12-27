package com.spring.techpractica.ui.rest.controller.admin.technology.get.by.field;

import com.spring.techpractica.application.admin.technology.get.by.field.GetTechnologiesByFieldCommand;
import com.spring.techpractica.application.admin.technology.get.by.field.GetTechnologiesByFieldUseCase;
import com.spring.techpractica.core.technology.entity.Technology;
import com.spring.techpractica.ui.rest.resources.technology.TechnologyResources;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/technologies")
@AllArgsConstructor
@Tag(name = "Admin - Technology")
@Validated
public class GetTechnologiesByFieldController {
    private final GetTechnologiesByFieldUseCase getTechnologiesByFieldUseCase;

    @Operation(summary = "Get technologies by fieldId", description = "Return technologies that belongs to the specific field.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Technologies returned",
                    content = @Content(schema = @Schema(implementation = TechnologyResources.class))),
            @ApiResponse(responseCode = "404", description = "Technologies not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request (e.g. empty names)", content = @Content)
    })
    @GetMapping("/{fieldId}")
    public ResponseEntity<?> getTechnologies(@PathVariable UUID fieldId) {


            List<Technology> technologies = getTechnologiesByFieldUseCase.execute(
                    new GetTechnologiesByFieldCommand(fieldId));

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

