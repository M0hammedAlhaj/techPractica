package com.spring.techpractica.UI.Rest.Controller.User.Profile.CompleteAccount.Request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;
import java.util.UUID;

public record CompleteAccountRequest(
        @Size(min = 3) @NotBlank String firstName,
        @Size(min = 3) @NotBlank String lastName,
        String brief,
        @Size(min = 3) @UniqueElements @NotNull List<UUID> skillsIds,
        @Size(min = 1, max = 4) List<@Valid SocialAccountRequest> socialAccountRequests
) {
}

