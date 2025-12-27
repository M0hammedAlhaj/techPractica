package com.spring.techpractica.ui.rest.controller.session.task.delete;

import com.spring.techpractica.application.session.task.delete.DeleteTaskCommand;
import com.spring.techpractica.application.session.task.delete.DeleteTaskUseCase;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.task.entity.Task;
import com.spring.techpractica.core.user.UserAuthentication;
import com.spring.techpractica.ui.rest.resources.session.SessionResources;
import com.spring.techpractica.ui.rest.resources.task.TaskResources;
import com.spring.techpractica.ui.rest.shared.StandardSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions/tasks")
@AllArgsConstructor
public class DeleteTaskController {

    private final DeleteTaskUseCase deleteTaskUseCase;

    @Operation(
            summary = "Delete existing Task",
            description = "Deletes an existing Task in a session. "
                    + "The user must be the session owner. Returns the deleted Task resource (after deletion)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task deleted successfully",
                    content = @Content(schema = @Schema(implementation = StandardSuccessResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request (e.g. malformed UUID)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized (invalid credentials)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden (user is not the owner of the session)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content
            )
    })
    @DeleteMapping("/")
    public ResponseEntity<?> deleteTask(
            @AuthenticationPrincipal UserAuthentication userAuthentication,
            @RequestBody DeleteTaskRequest request) {

        Task task = deleteTaskUseCase.execute(new DeleteTaskCommand(
                userAuthentication.getUserId(),
                request.sessionId(),request.taskId()));

        TaskResources responseData = new TaskResources(task);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        StandardSuccessResponse.<TaskResources>builder()
                                .data(responseData)
                                .message("Task deleted successfully")
                                .status(HttpStatus.OK.value())
                                .build()
                );
    }
}
