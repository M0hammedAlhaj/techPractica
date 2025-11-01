package com.spring.techpractica.ui.rest.controller.admin.system.get.all;

import com.spring.techpractica.application.admin.system.get.all.GetAllSystemsCommand;
import com.spring.techpractica.application.admin.system.get.all.GetAllSystemsUseCase;
import com.spring.techpractica.core.system.entity.System;
import com.spring.techpractica.ui.rest.resources.system.SystemCollection;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/systems")
@AllArgsConstructor
@Tag(name = "Admin - System")
@Validated
public class GetAllSystemsController {
    private final GetAllSystemsUseCase getAllSystemsUseCase;

    @Operation(summary = "Get all systems", description = "Return all systems available in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Systems returned",
                    content = @Content(schema = @Schema(implementation = SystemCollection.class))),
            @ApiResponse(responseCode = "404", description = "No systems found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<?> getAllSystems() {

            List<System> systems = getAllSystemsUseCase.execute(new GetAllSystemsCommand());

            SystemCollection responseDataList = new SystemCollection(systems);

            return ResponseEntity.status(HttpStatus.OK).body(
                    StandardSuccessResponse.builder()
                            .data(responseDataList)
                            .message("Systems returned successfully")
                            .status(HttpStatus.OK.value())
                            .build()
            );

    }
}
