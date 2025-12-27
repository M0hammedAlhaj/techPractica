package com.spring.techpractica.ui.rest.controller.admin.technology.delete;

import com.spring.techpractica.application.admin.technology.delete.DeleteTechnologyCommand;
import com.spring.techpractica.application.admin.technology.delete.DeleteTechnologyUseCase;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/technologies")
@AllArgsConstructor
@Tag(name = "Admin - Technology")
public class DeleteTechnologyController {

    private final DeleteTechnologyUseCase deleteTechnologyUseCase;

    @Operation(summary = "Delete Technology", description = "Admin delete a Technology and returns the deleted id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Technology deleted successfully",
                    content = @Content(schema = @Schema(implementation = UUID.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized (invalid credentials)", content = @Content),
            @ApiResponse(responseCode = "409", description = "Field with the same name already exists", content = @Content)
    })
    @DeleteMapping("/{technologyId}/")
    public ResponseEntity<?> deleteTechnology(@PathVariable UUID technologyId) {

        deleteTechnologyUseCase.execute(new DeleteTechnologyCommand(technologyId));

        return ResponseEntity.status(HttpStatus.OK).body(
                StandardSuccessResponse.<UUID>builder()
                        .data(technologyId)
                        .message("Technology deleted successfully")
                        .status(HttpStatus.OK.value())
                        .build()
        );
    }
}

