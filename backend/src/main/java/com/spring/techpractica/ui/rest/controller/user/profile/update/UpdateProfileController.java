package com.spring.techpractica.ui.rest.controller.user.profile.update;

import com.spring.techpractica.application.user.profile.update.UpdateProfileCommand;
import com.spring.techpractica.application.user.profile.update.UpdateProfileUseCase;
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
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/profile")
@Tag(name = "Profile", description = "Profile related endpoints")
public class UpdateProfileController {

    private final UpdateProfileUseCase updateProfileUseCase;

    @Operation(
            summary = "Update user profile",
            description = "Allows an authenticated user to update their profile information such as first name, last name, brief, skills, and social accounts.",
            tags = {"Profile"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Profile updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardSuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed (invalid data provided)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized (missing or invalid JWT token)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardErrorResponse.class)))
    })
    @PutMapping("/")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid ProfileRequest request,
                                           @AuthenticationPrincipal UserAuthentication userAuthentication) {

        User user = updateProfileUseCase.execute(new UpdateProfileCommand(
                userAuthentication.getUserId(),
                request.firstName(),
                request.lastName(),
                request.brief(),
                new HashSet<>(request.skillsIds()),
                request.socialAccountRequests().stream()
                        .map(socialAccountRequest -> new SocialAccountRequest(
                                socialAccountRequest.getPlatformName(),
                                socialAccountRequest.getProfileUrl())
                        )
                        .toList()
        ));

        UserResources response = new UserResources(user);

        return ResponseEntity.accepted().body(StandardSuccessResponse.builder()
                .data(response)
                .message("Profile updated successfully")
                .status(HttpStatus.ACCEPTED.value())
                .build());
    }

}