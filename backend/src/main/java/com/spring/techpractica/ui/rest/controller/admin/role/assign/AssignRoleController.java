package com.spring.techpractica.ui.rest.controller.admin.role.assign;

import com.spring.techpractica.application.admin.role.assign.AssignRoleCommand;
import com.spring.techpractica.application.admin.role.assign.AssignRoleUseCase;
import com.spring.techpractica.ui.rest.shared.StandardSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
@AllArgsConstructor
public class AssignRoleController {

    private final AssignRoleUseCase assignRoleUseCase;

    @Operation(
            summary = "Assign roles to a user",
            description = "Allows admin to assign one or multiple roles to a specific user by their ID.",
            requestBody = @RequestBody(
                    required = true,
                    description = "User ID and roles list to assign",
                    content = @Content(
                            schema = @Schema(implementation = AssignRoleRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Roles assigned successfully",
                            content = @Content(
                                    schema = @Schema(implementation = StandardSuccessResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content
                    )
            }
    )
    @PutMapping("/assign")
    public ResponseEntity<?> assignRole(@Valid @RequestBody AssignRoleRequest request) {
        String result = assignRoleUseCase.execute(new AssignRoleCommand(request.id(), request.roleIds()));

        return ResponseEntity.ok(StandardSuccessResponse.<String>builder()
                .data(result)
                .message("Role Assigned Successfully")
                .status(HttpStatus.OK.value())
                .build()
        );
    }
}
