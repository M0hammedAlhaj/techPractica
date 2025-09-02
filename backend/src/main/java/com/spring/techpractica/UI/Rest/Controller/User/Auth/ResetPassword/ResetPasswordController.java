package com.spring.techpractica.UI.Rest.Controller.User.Auth.ResetPassword;

import com.spring.techpractica.Application.User.Auth.ResetPassword.ResetPasswordCommand;
import com.spring.techpractica.Application.User.Auth.ResetPassword.ResetPasswordUseCase;
import com.spring.techpractica.UI.Rest.Shared.StandardErrorResponse;
import com.spring.techpractica.UI.Rest.Shared.StandardSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication related endpoints")
public class ResetPasswordController {

    private final ResetPasswordUseCase useCase;

    public ResetPasswordController(ResetPasswordUseCase useCase) {
        this.useCase = useCase;
    }

    @Operation(
            summary = "Request password reset",
            description = "Sends a reset password email to the user with a secure reset link.",
            tags = {"Authentication"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "If this email exists, a reset link has been sent!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardSuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardErrorResponse.class)))
    })
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        useCase.execute(new ResetPasswordCommand(request.email()));
        return ResponseEntity.accepted().body(StandardSuccessResponse.builder()
                .data(request.email())
                .message("If this email exists, a reset link has been sent!")
                .status(HttpStatus.ACCEPTED.value())
                .build());
    }
}