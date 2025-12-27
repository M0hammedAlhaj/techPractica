package com.spring.techpractica.ui.rest.controller.user.auth.login;

import com.spring.techpractica.application.user.auth.login.LoginAccountCommand;
import com.spring.techpractica.application.user.auth.login.LoginAccountUseCase;
import com.spring.techpractica.core.user.exception.UserAuthenticationException;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.ui.rest.shared.StandardErrorResponse;
import com.spring.techpractica.ui.rest.shared.StandardSuccessResponse;
import com.spring.techpractica.infrastructure.jwt.JwtGeneration;
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
            String token = jwtGeneration.generateLoginToken(user.getId(), user.getName(), user.getRoles());

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
