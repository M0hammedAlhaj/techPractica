package com.spring.techpractica.UI.Rest.Controller.Admin.System.DeleteSystem;

import com.spring.techpractica.Application.Admin.Field.DeleteField.DeleteFieldCommand;
import com.spring.techpractica.Application.Admin.Field.DeleteField.DeleteFieldUseCase;
import com.spring.techpractica.Application.Admin.System.DeleteSystem.DeleteSystemCommand;
import com.spring.techpractica.Application.Admin.System.DeleteSystem.DeleteSystemUseCase;
import com.spring.techpractica.Application.Session.delete.DeleteSessionUseCase;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/systems")
@AllArgsConstructor
@Tag(name = "Admin - System")
public class DeleteSystemController {

    private final DeleteSystemUseCase deleteSystemUseCase;

    @Operation(summary = "Delete System", description = "Admin delete a System and returns the deleted id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "System deleted successfully",
                    content = @Content(schema = @Schema(implementation = UUID.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized (invalid credentials)", content = @Content),
            @ApiResponse(responseCode = "409", description = "Field with the same name already exists", content = @Content)
    })
    @DeleteMapping("/{systemId}/")
    public ResponseEntity<?> deleteSystem(@PathVariable UUID systemId) {

        deleteSystemUseCase.execute(new DeleteSystemCommand(systemId));

        return ResponseEntity.status(HttpStatus.OK).body(
                StandardSuccessResponse.<UUID>builder()
                        .data(systemId)
                        .message("System deleted successfully")
                        .status(HttpStatus.OK.value())
                        .build()
        );
    }
}

