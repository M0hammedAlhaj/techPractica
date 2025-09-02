package com.spring.techpractica.UI.Rest.Controller.User.Profile.CompleteAccount;

import com.spring.techpractica.Application.User.Profile.CompleteAccount.CompleteAccountCommand;
import com.spring.techpractica.Application.User.Profile.CompleteAccount.CompleteAccountUseCase;
import com.spring.techpractica.Core.SocialAccount.model.SocialAccountRequest;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.User.UserAuthentication;
import com.spring.techpractica.UI.Rest.Controller.User.Profile.CompleteAccount.Request.CompleteAccountRequest;
import com.spring.techpractica.UI.Rest.Resources.User.UserResources;
import com.spring.techpractica.UI.Rest.Shared.StandardErrorResponse;
import com.spring.techpractica.UI.Rest.Shared.StandardSuccessResponse;
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
    public ResponseEntity<?> completeAccount(@RequestBody @Valid CompleteAccountRequest request
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