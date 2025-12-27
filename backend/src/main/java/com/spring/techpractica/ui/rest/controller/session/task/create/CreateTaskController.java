package com.spring.techpractica.ui.rest.controller.session.task.create;

import com.spring.techpractica.application.session.task.create.CreateTaskCommand;
import com.spring.techpractica.application.session.task.create.CreateTaskUseCase;
import com.spring.techpractica.core.task.entity.Task;
import com.spring.techpractica.core.task.model.TaskType;
import com.spring.techpractica.ui.rest.resources.task.TaskResources;
import com.spring.techpractica.core.user.UserAuthentication;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions/tasks")
@AllArgsConstructor
public class CreateTaskController {

    private final CreateTaskUseCase createTaskUseCase;

    @Operation(
            summary = "Create a new task for a session",
            description = "Creates a new task in a specific session. The user must be authenticated."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = @Content(schema = @Schema(implementation = StandardSuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Session not found", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<?> createTask(@RequestBody CreateTaskRequest request,
                                        @AuthenticationPrincipal UserAuthentication userAuthentication) {
        UUID userId = userAuthentication.getUserId();

        Task task = createTaskUseCase.execute(
                new CreateTaskCommand(
                        userId,
                        request.sessionId(),
                        request.title(),
                        request.description(),
                        TaskType.valueOf(request.type().toUpperCase()),
                        request.dueDate(),
                        request.assignees(),
                        request.tags()
                )
        );

        TaskResources data = new TaskResources(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                StandardSuccessResponse.<TaskResources>builder()
                        .data(data)
                        .message("Task created successfully")
                        .status(HttpStatus.CREATED.value())
                        .build()
        );
    }
}