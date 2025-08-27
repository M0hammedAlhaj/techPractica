package com.spring.techpractica.Application.User.Profile.CompleteAccount;

import com.spring.techpractica.Core.SocialAccount.model.SocialAccountRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CompleteAccountCommand(UUID userId
        , String firstName
        , String lastName
        , String brief
        ,  List<UUID> skillsIds
        , List<SocialAccountRequest> socialAccountRequests) {

}
