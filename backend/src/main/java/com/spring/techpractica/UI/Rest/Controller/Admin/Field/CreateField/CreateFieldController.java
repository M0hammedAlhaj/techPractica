package com.spring.techpractica.UI.Rest.Controller.Admin.Field.CreateField;

import com.spring.techpractica.Application.Admin.Field.CreateField.CreateFieldCommand;
import com.spring.techpractica.Application.Admin.Field.CreateField.CreateFieldUseCase;
import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Shared.Exception.ResourcesDuplicateException;
import com.spring.techpractica.UI.Rest.Resources.Field.FieldResources;
import com.spring.techpractica.UI.Rest.Shared.StandardErrorResponse;
import com.spring.techpractica.UI.Rest.Shared.StandardSuccessResponse;
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

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/admin/fields")
@AllArgsConstructor
@Tag(name = "Admin - Field")
public class CreateFieldController {
    private final CreateFieldUseCase createFieldUseCase;

    @Operation(summary = "Admin create field", description = "Admin create field and returns the data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = FieldResources.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request payload",
                    content = @Content),

    })
    @PostMapping("/")
 //   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createField(@RequestBody @Valid CreateFieldRequest request) {
        try {
            Field field =createFieldUseCase.execute(new CreateFieldCommand(request.name()));

            FieldResources responseData = FieldResources
                    .builder()
                    .id(field.getId())
                    .name(field.getName())
                    .build();

            return ResponseEntity.ok(StandardSuccessResponse.<FieldResources>builder()
                    .data(responseData)
                    .message("Field created successfully")
                    .status(HttpStatus.OK.value())
                    .build());
        } catch (ResourcesDuplicateException ex) {
            StandardErrorResponse response = StandardErrorResponse.builder()
                    .timestamp(Instant.now())
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message(ex.getMessage())
                    .code("Exists_FIELD")
                    .build();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
