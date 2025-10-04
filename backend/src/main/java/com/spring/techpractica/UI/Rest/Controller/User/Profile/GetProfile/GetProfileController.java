package com.spring.techpractica.UI.Rest.Controller.User.Profile.GetProfile;

import com.spring.techpractica.Application.Session.GetSessions.GetSessionsCount.GetSessionsCountUseCase;
import com.spring.techpractica.Application.Session.GetSessions.GetUserSessionsCount.GetUserSessionsCountCommand;
import com.spring.techpractica.Application.Session.GetSessions.GetUserSessionsCount.GetUserSessionsCountUseCase;
import com.spring.techpractica.Application.Session.GetSessions.UserSessions.GetUserSessionCommand;
import com.spring.techpractica.Application.Session.GetSessions.UserSessions.GetUserSessionsUseCase;
import com.spring.techpractica.Application.User.Profile.GetProfile.GetProfileCommand;
import com.spring.techpractica.Application.User.Profile.GetProfile.GetProfileUseCase;
import com.spring.techpractica.Application.User.Profile.UpdateProfile.UpdateProfileCommand;
import com.spring.techpractica.Application.User.Profile.UpdateProfile.UpdateProfileUseCase;
import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.SocialAccount.model.SocialAccountRequest;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.User.UserAuthentication;
import com.spring.techpractica.UI.Rest.Controller.User.Profile.Request.ProfileRequest;
import com.spring.techpractica.UI.Rest.Resources.Session.SessionCollection;
import com.spring.techpractica.UI.Rest.Resources.User.ProfileResources;
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
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/profile")
@Tag(name = "Profile", description = "Profile related endpoints")
public class GetProfileController {

    private final GetProfileUseCase getProfileUseCase;

    private final GetUserSessionsUseCase getUserSessionsUseCase;

    private final GetUserSessionsCountUseCase getUserSessionsCountUseCase;


    @Operation(
            summary = "Get user profile",
            description = "Allows an authenticated user to Get their profile information such as first name, last name, brief, skills, and social accounts and there sessions.",
            tags = {"Profile"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile returned successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardSuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed (invalid data provided)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized (missing or invalid JWT token)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardErrorResponse.class)))
    })
    @GetMapping("/")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserAuthentication userAuthentication) {

        User user = getProfileUseCase.execute(new GetProfileCommand(
                userAuthentication.getUserId()));

        List<Session> sessions =getUserSessionsUseCase.execute(new GetUserSessionCommand(user.getId(),6,0)).getContent();

        ProfileResources response = new ProfileResources(new UserResources(user)
                ,new SessionCollection(sessions,getUserSessionsCountUseCase.execute(new GetUserSessionsCountCommand(user.getId()))));

        return ResponseEntity.accepted().body(StandardSuccessResponse.builder()
                .data(response)
                .message("Profile returned successfully")
                .status(HttpStatus.ACCEPTED.value())
                .build());
    }

}