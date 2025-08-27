package com.spring.techpractica.UI.Rest.Controller.User.Auth.LoginAccount;

import com.spring.techpractica.Application.User.Auth.LoginAccount.LoginAccountCommand;
import com.spring.techpractica.Application.User.Auth.LoginAccount.LoginAccountUseCase;
import com.spring.techpractica.Core.User.Exception.UserAuthenticationException;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.UI.Rest.Shared.StandardErrorResponse;
import com.spring.techpractica.UI.Rest.Shared.StandardSuccessResponse;
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

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Tag(name = "Authentication")
public class LoginAccountController {

    private final LoginAccountUseCase loginAccountUseCase;
    private final JwtGeneration jwtGeneration;

    @Operation(summary = "Login user account", description = "Authenticates user and returns a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login account successful",
                    content = @Content(schema = @Schema(implementation = LoginAccountResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request payload",
                    content = @Content),
    })
    @PostMapping("/login")
    public ResponseEntity<?> loginAccount(@RequestBody @Valid LoginAccountRequest request) {
        try {
            User user = loginAccountUseCase.execute(new LoginAccountCommand(request.email(), request.password()));
            String token = jwtGeneration.generateToken(user.getId(), user.getName());

            return ResponseEntity.ok(StandardSuccessResponse.<LoginAccountResponse>builder()
                    .data(new LoginAccountResponse(user, token))
                    .message("Login account successful")
                    .status(HttpStatus.OK.value())
                    .build());
        } catch (UserAuthenticationException ex) {
            StandardErrorResponse response = StandardErrorResponse.builder()
                    .timestamp(Instant.now())
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message(ex.getMessage())
                    .code("AUTH_FAILED")
                    .build();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
