package com.spring.techpractica.UI.Rest.Controller.Admin.System.GetSystem.GetAllSystems;

import com.spring.techpractica.Application.Admin.System.GetSystem.GetAllSystems.GetAllSystemsCommand;
import com.spring.techpractica.Application.Admin.System.GetSystem.GetAllSystems.GetAllSystemsUseCase;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.System.Entity.System;
import com.spring.techpractica.UI.Rest.Resources.System.SystemCollection;
import com.spring.techpractica.UI.Rest.Resources.System.SystemResources;
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
