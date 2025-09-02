package com.spring.techpractica.UI.Rest.Controller.User.Auth.ChangePassword;

import com.spring.techpractica.Application.User.Auth.ChangePassword.ChangePasswordCommand;
import com.spring.techpractica.Application.User.Auth.ChangePassword.ChangePasswordUseCase;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.User.UserAuthentication;
import com.spring.techpractica.UI.Rest.Resources.User.UserResources;
import com.spring.techpractica.UI.Rest.Shared.StandardErrorResponse;
import com.spring.techpractica.UI.Rest.Shared.StandardSuccessResponse;
import com.spring.techpractica.infrastructure.Jwt.JwtExtracting;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Tag(name = "Authentication", description = "Authentication related endpoints")
public class ChangePasswordController {

    private final ChangePasswordUseCase useCase;

    @Operation(
            summary = "Change password",
            description = "Allows an authenticated user to change their password.",
            tags = {"Authentication"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Password changed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardSuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Password does not match",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardErrorResponse.class)))
    })
    @PostMapping(value = "/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequest request,
                                            @RequestParam String token,
                                            @AuthenticationPrincipal UserAuthentication userAuthentication) {
        String password = request.password();
        String confirmPassword = request.confirmPassword();

        if (!confirmPassword.equals(password)) {
            return ResponseEntity.badRequest().body(StandardErrorResponse
                    .builder()
                    .timestamp(Instant.now())
                    .message("Password does not match")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .code(HttpStatus.BAD_REQUEST.getReasonPhrase()));
        }
        User user = useCase.execute(new ChangePasswordCommand(userAuthentication.getUserId(), password));
        return ResponseEntity.accepted()
                .body(StandardSuccessResponse.builder()
                        .data(new UserResources(user))
                        .status(HttpStatus.ACCEPTED.value())
                        .message("Change Password Successful").build());
    }
}
