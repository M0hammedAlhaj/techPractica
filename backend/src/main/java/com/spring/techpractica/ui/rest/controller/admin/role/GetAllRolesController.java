package com.spring.techpractica.ui.rest.controller.admin.role;

import com.spring.techpractica.application.admin.role.get.all.GetAllRolesUseCase;
import com.spring.techpractica.core.role.entity.Role;
import com.spring.techpractica.ui.rest.resources.role.RoleCollection;
import com.spring.techpractica.ui.rest.shared.StandardSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@AllArgsConstructor
public class GetAllRolesController {

    private final GetAllRolesUseCase getAllRolesUseCase;

    @Operation(
            summary = "Get all roles",
            description = "Fetch all available roles in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles fetched successfully",
                    content = @Content(schema = @Schema(implementation = StandardSuccessResponse.class))),
    })
    @GetMapping("/")
    public ResponseEntity<?> getAllRoles(){
        List<Role> roles = getAllRolesUseCase.getAllRoles();

        RoleCollection roleCollection = new RoleCollection(roles);

        return ResponseEntity.ok(StandardSuccessResponse.<RoleCollection>builder()
                .data(roleCollection)
                .message("Get all roles done successfully")
                .status(HttpStatus.OK.value())
                .build());
    }
}
