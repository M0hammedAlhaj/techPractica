package com.spring.techpractica.ui.rest.controller.admin.system.get.by.name;

import com.spring.techpractica.application.admin.system.get.by.name.GetSystemsByNameCommand;
import com.spring.techpractica.application.admin.system.get.by.name.GetSystemsByNameUseCase;
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
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

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


