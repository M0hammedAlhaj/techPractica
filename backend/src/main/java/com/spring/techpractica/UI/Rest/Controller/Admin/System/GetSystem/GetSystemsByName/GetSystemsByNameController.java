package com.spring.techpractica.UI.Rest.Controller.Admin.System.GetSystem.GetSystemsByName;

import com.spring.techpractica.Application.Admin.System.GetSystem.GetSystemsByName.GetSystemsByNameCommand;
import com.spring.techpractica.Application.Admin.System.GetSystem.GetSystemsByName.GetSystemsByNameUseCase;
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
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/systems")
@AllArgsConstructor
@Tag(name = "Admin - System")
@Validated
public class GetSystemsByNameController {
    private final GetSystemsByNameUseCase getSystemsByNameUseCase;

    @Operation(summary = "Get systems by names", description = "Return systems that match the provided names (use query parameter `names`).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Systems returned",
                    content = @Content(schema = @Schema(implementation = SystemCollection.class))),
            @ApiResponse(responseCode = "404", description = "One or more Systems not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request (e.g. empty names)", content = @Content)
    })
    @GetMapping("/name")
    public ResponseEntity<?> getSystems(
            @RequestParam(name = "names") @NotEmpty @Size(min = 1) List<@NotBlank String> names) {


            List<System> systems = getSystemsByNameUseCase.execute(new GetSystemsByNameCommand(names));

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


