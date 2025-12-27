package com.spring.techpractica.ui.rest.controller.session.task.get;

import com.spring.techpractica.application.session.task.get.GetTaskCommand;
import com.spring.techpractica.application.session.task.get.GetTaskUseCase;
import com.spring.techpractica.core.task.entity.Task;
import com.spring.techpractica.core.user.UserAuthentication;
import com.spring.techpractica.ui.rest.resources.task.TaskCollection;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions/tasks")
@AllArgsConstructor
public class GetTaskController {

    private final GetTaskUseCase getTaskUseCase;

    @Operation(
            summary = "Get Tasks",
            description = "Get the tasks in the session. "
                    + "The user must be session participant. Returns the all Tasks in the session ."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tasks returned successfully",
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
                    description = "Forbidden (user is not a participant of the session)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Session not found",
                    content = @Content
            )
    })
    @GetMapping("/")
    public ResponseEntity<?> getTask(
            @AuthenticationPrincipal UserAuthentication userAuthentication,
            @RequestParam UUID sessionId) {

        List<Task> tasks = getTaskUseCase.execute(new GetTaskCommand(
                userAuthentication.getUserId(),
                sessionId));

        TaskCollection responseData = new TaskCollection(tasks);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        StandardSuccessResponse.<TaskCollection>builder()
                                .data(responseData)
                                .message("Tasks returned successfully")
                                .status(HttpStatus.OK.value())
                                .build()
                );
    }
}
