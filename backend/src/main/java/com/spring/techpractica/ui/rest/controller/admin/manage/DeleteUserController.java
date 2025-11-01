package com.spring.techpractica.ui.rest.controller.admin.manage;

import com.spring.techpractica.application.admin.manage.delete.user.DeleteUserCommand;
import com.spring.techpractica.application.admin.manage.delete.user.DeleteUserUseCase;
import com.spring.techpractica.ui.rest.shared.StandardSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class DeleteUserController {

    private final DeleteUserUseCase  deleteUserUseCase;

    @Operation(
            summary = "Delete a user",
            description = "Deletes a user by their unique UUID. Only accessible by administrators."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardSuccessResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @DeleteMapping("/delete/{userId}") public ResponseEntity<?> deleteUser(@PathVariable UUID userId) {
        String result = deleteUserUseCase.execute(new DeleteUserCommand(userId));

        return ResponseEntity.ok(StandardSuccessResponse.<String>builder()
                .data(result)
                .message("Delete User Successfully")
                .status(HttpStatus.OK.value())
                .build());
    }
}