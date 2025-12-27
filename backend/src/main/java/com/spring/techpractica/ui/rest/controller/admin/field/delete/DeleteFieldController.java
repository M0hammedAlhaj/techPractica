package com.spring.techpractica.ui.rest.controller.admin.field.delete;

import com.spring.techpractica.application.admin.field.delete.DeleteFieldCommand;
import com.spring.techpractica.application.admin.field.delete.DeleteFieldUseCase;
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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/fields")
@AllArgsConstructor
@Tag(name = "Admin - Field")
public class DeleteFieldController {

    private final DeleteFieldUseCase deleteFieldUseCase;

    @Operation(summary = "Delete Field", description = "Admin delete a Field and returns the deleted id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Field deleted successfully",
                    content = @Content(schema = @Schema(implementation = UUID.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized (invalid credentials)", content = @Content),
            @ApiResponse(responseCode = "409", description = "Field with the same name already exists", content = @Content)
    })
    @DeleteMapping("/{fieldId}/")
    public ResponseEntity<?> deleteField(@PathVariable UUID fieldId) {

        deleteFieldUseCase.execute(new DeleteFieldCommand(fieldId));


        return ResponseEntity.status(HttpStatus.OK).body(
                StandardSuccessResponse.<UUID>builder()
                        .data(fieldId)
                        .message("Field deleted successfully")
                        .status(HttpStatus.OK.value())
                        .build()
        );
    }
}

