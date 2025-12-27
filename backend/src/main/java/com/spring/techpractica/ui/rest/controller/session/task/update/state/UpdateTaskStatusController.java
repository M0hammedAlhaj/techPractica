package com.spring.techpractica.ui.rest.controller.session.task.update.state;

import com.spring.techpractica.application.session.task.update.state.UpdateTaskStatusCommand;
import com.spring.techpractica.application.session.task.update.state.UpdateTaskStatusUseCase;
import com.spring.techpractica.core.task.entity.Task;
import com.spring.techpractica.core.task.model.TaskStatus;
import com.spring.techpractica.ui.rest.resources.task.TaskResources;
import com.spring.techpractica.ui.rest.shared.StandardSuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions/tasks")
@AllArgsConstructor
public class UpdateTaskStatusController {

    private final UpdateTaskStatusUseCase updateTaskStatusUseCase;

    @Operation(
            summary = "Updates the status of a task",
            description = "Updates the status of a specific task within a session. The user must be authenticated."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task status updated successfully",
                    content = @Content(schema = @Schema(implementation = StandardSuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
    })
    @PutMapping("/{taskId}/update-status")
    public ResponseEntity<?> updateTaskStatus(@PathVariable(name = "taskId") UUID taskId, @RequestBody UpdateTaskStatusRequest request){

        Task task = updateTaskStatusUseCase.execute(
                new UpdateTaskStatusCommand(taskId, TaskStatus.valueOf(request.status().toUpperCase())));

        TaskResources responseData = new TaskResources(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                StandardSuccessResponse.<TaskResources>builder()
                        .data(responseData)
                        .message("Task status updated successfully")
                        .status(HttpStatus.OK.value())
                        .build()
        );
    }
}
