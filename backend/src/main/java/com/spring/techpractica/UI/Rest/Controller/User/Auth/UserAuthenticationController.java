package com.spring.techpractica.UI.Rest.Controller.User.Auth;

import com.spring.techpractica.Application.User.LoginAccount.LoginAccountCommand;
import com.spring.techpractica.Application.User.LoginAccount.LoginAccountUseCase;
import com.spring.techpractica.Application.User.RegisterAccount.RegisterAccountCommand;
import com.spring.techpractica.Application.User.RegisterAccount.RegisterAccountUseCase;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.UI.Rest.Resources.User.Authentication.Request.LoginAccountRequest;
import com.spring.techpractica.UI.Rest.Resources.User.Authentication.Request.RegisterAccountRequest;
import com.spring.techpractica.UI.Rest.Resources.User.Authentication.Response.LoginAccountResponse;
import com.spring.techpractica.UI.Rest.Resources.User.UserResources;
import com.spring.techpractica.UI.Rest.Shared.StandardResponse;
import com.spring.techpractica.infrastructure.Jwt.JwtGeneration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Tag(name = "Authentication")
public class UserAuthenticationController {

    private final RegisterAccountUseCase registerAccountUseCase;
    private final LoginAccountUseCase loginAccountUseCase;
    private final JwtGeneration jwtGeneration;

    @Operation(summary = "Register a new user account", description = "Creates a new user account with name, email, and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully registered",
                    content = @Content(schema = @Schema(implementation = UserResources.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Email already in use",
                    content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<StandardResponse<UserResources>> registerAccount(@RequestBody @Valid RegisterAccountRequest request) {
        User user = registerAccountUseCase.execute(new RegisterAccountCommand(request.name(), request.email(), request.password()));

        StandardResponse<UserResources> response = StandardResponse.<UserResources>builder()
                .data(new UserResources(user))
                .message("Register account successful")
                .status(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Login user account", description = "Authenticates user and returns a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = LoginAccountResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<StandardResponse<LoginAccountResponse>> loginAccount(@RequestBody @Valid LoginAccountRequest request) {
        User user = loginAccountUseCase.execute(new LoginAccountCommand(request.email(), request.password()));
        String token = jwtGeneration.generateToken(user.getId(), user.getName());

        return ResponseEntity.ok(StandardResponse.<LoginAccountResponse>builder()
                .data(new LoginAccountResponse(user, token))
                .message("Login account successful")
                .status(HttpStatus.OK.value())
                .build());
    }
}

