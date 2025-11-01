package com.spring.techpractica.ui.rest.controller.user.profile.complete;

import com.spring.techpractica.application.user.profile.complete.CompleteAccountCommand;
import com.spring.techpractica.application.user.profile.complete.CompleteAccountUseCase;
import com.spring.techpractica.core.social.account.model.SocialAccountRequest;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserAuthentication;
import com.spring.techpractica.ui.rest.controller.user.profile.request.ProfileRequest;
import com.spring.techpractica.ui.rest.resources.user.UserResources;
import com.spring.techpractica.ui.rest.shared.StandardErrorResponse;
import com.spring.techpractica.ui.rest.shared.StandardSuccessResponse;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/profile")
@Tag(name = "Profile", description = "Profile related endpoints")
public class CompleteAccountController {

    private final CompleteAccountUseCase completeAccountUseCase;

    @Operation(
            summary = "Complete user account",
            description = "Allows an authenticated user to complete their account by adding first name, last name, brief, skills, and social accounts.",
            tags = {"Profile"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Account completed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardSuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed (invalid data provided)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized (missing or invalid JWT token)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardErrorResponse.class)))
    })
    @PostMapping("/")
    public ResponseEntity<?> completeAccount(@RequestBody @Valid ProfileRequest request
            , @AuthenticationPrincipal UserAuthentication userAuthentication) {

        User user = completeAccountUseCase.execute(new CompleteAccountCommand(userAuthentication.getUserId(),
                request.firstName(),
                request.lastName(),
                request.brief(),
                request.skillsIds(),
                request.socialAccountRequests().stream().map(
                        socialAccountRequest -> new SocialAccountRequest(socialAccountRequest.getPlatformName()
                                , socialAccountRequest.getProfileUrl())
                ).toList()
        ));
        UserResources response = new UserResources(user);

        return ResponseEntity.accepted().body(StandardSuccessResponse.builder()
                .data(response)
                .message("Account completed successfully")
                .status(HttpStatus.ACCEPTED.value())
                .build());
    }
}