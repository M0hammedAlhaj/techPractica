package com.spring.techpractica.UI.Rest.Controller.Admin.Field.GetField.GetAllFields;

import com.spring.techpractica.Application.Admin.Field.GetField.GetAllFields.GetAllFieldsCommand;
import com.spring.techpractica.Application.Admin.Field.GetField.GetAllFields.GetAllFieldsUseCase;
import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.UI.Rest.Resources.Field.FieldCollection;
import com.spring.techpractica.UI.Rest.Resources.Field.FieldResources;
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
@RequestMapping("/api/v1/admin/fields")
@AllArgsConstructor
@Tag(name = "Admin - Field")
@Validated
public class GetAllFieldsController {
    private final GetAllFieldsUseCase getAllFieldsUseCase;
    @Operation(summary = "Get all fields", description = "Return all fields available in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fields returned",
                    content = @Content(schema = @Schema(implementation = FieldCollection.class))),
            @ApiResponse(responseCode = "404", description = "No fields found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<?> getAllSystems() {

            List<Field> fields = getAllFieldsUseCase.execute(new GetAllFieldsCommand());

            FieldCollection responseDataList = new FieldCollection(fields);

            return ResponseEntity.status(HttpStatus.OK).body(
                    StandardSuccessResponse.builder()
                            .data(responseDataList)
                            .message("Fields returned successfully")
                            .status(HttpStatus.OK.value())
                            .build()
            );

    }


}
