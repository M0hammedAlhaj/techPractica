package com.spring.techpractica.application.user.profile.complete;

import com.spring.techpractica.core.social.account.model.SocialAccountRequest;

import java.util.List;
import java.util.UUID;

public record CompleteAccountCommand(UUID userId
        , String firstName
        , String lastName
        , String brief
        ,  List<UUID> skillsIds
        , List<SocialAccountRequest> socialAccountRequests) {

}
