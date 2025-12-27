package com.spring.techpractica.ui.rest.controller.user.auth.active.account;

import com.spring.techpractica.application.user.auth.active.account.ActiveAccountCommand;
import com.spring.techpractica.application.user.auth.active.account.ActiveAccountUseCase;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserAuthentication;
import com.spring.techpractica.ui.rest.resources.user.UserResources;
import com.spring.techpractica.ui.rest.shared.StandardSuccessResponse;
import com.spring.techpractica.infrastructure.jwt.JwtExtracting;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Tag(name = "Authentication", description = "Endpoints related to user authentication and account activation")
public class ActiveAccountController {

    private final JwtExtracting jwtExtracting;
    private final ActiveAccountUseCase useCase;

    /**
     * Activates a user account based on the provided JWT token.
     *
     * @param token JWT token containing the user's ID
     * @return ResponseEntity containing the activated user details
     */
    @Operation(
            summary = "Activate User Account",
            description = "Verifies the provided JWT token and activates the corresponding user account."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Account successfully activated"),
            @ApiResponse(responseCode = "400", description = "Invalid token provided"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/active-account")
    public ResponseEntity<?> verifyToken(@RequestParam String token,
                                         @AuthenticationPrincipal UserAuthentication userAuthentication) {
        User user = useCase.execute(new ActiveAccountCommand(userAuthentication.getUserId()));

        return ResponseEntity.status(HttpStatus.ACCEPTED.value())
                .body(StandardSuccessResponse.builder()
                        .data(new UserResources(user))
                        .message("Active account has been verified")
                        .status(HttpStatus.ACCEPTED.value())
                        .build());
    }
}


