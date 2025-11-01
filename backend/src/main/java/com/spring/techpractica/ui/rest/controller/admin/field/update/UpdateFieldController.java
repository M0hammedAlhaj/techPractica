package com.spring.techpractica.ui.rest.controller.admin.field.update;

import com.spring.techpractica.application.admin.field.update.UpdateFieldCommand;
import com.spring.techpractica.application.admin.field.update.UpdateFieldUseCase;
import com.spring.techpractica.core.field.entity.Field;
import com.spring.techpractica.ui.rest.resources.field.FieldResources;
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
@RequestMapping("/api/v1/admin/fields")
@AllArgsConstructor
@Tag(name = "Admin - Field")
public class UpdateFieldController {

    private final UpdateFieldUseCase updateFieldUseCase;

    @Operation(summary = "Update Field", description = "Admin update a Field and returns the updated resource.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Field updated successfully",
                    content = @Content(schema = @Schema(implementation = FieldResources.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized (invalid credentials)", content = @Content),
            @ApiResponse(responseCode = "409", description = "Field with the same name already exists", content = @Content)
    })
    @PutMapping("/{fieldId}/")
    public ResponseEntity<?> updateField(@RequestBody @Valid UpdateFieldRequest request, @PathVariable UUID fieldId) {
        Field field = updateFieldUseCase.execute(new UpdateFieldCommand(fieldId,request.name()));

        FieldResources responseData = FieldResources.builder()
                .id(field.getId())
                .name(field.getName())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(
                StandardSuccessResponse.<FieldResources>builder()
                        .data(responseData)
                        .message("Field updated successfully")
                        .status(HttpStatus.CREATED.value())
                        .build()
        );
    }
}

