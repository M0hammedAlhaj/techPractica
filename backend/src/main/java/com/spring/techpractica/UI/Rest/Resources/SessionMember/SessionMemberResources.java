package com.spring.techpractica.UI.Rest.Resources.SessionMember;

import com.spring.techpractica.Core.SessionMembers.Entity.SessionMember;
import com.spring.techpractica.Core.SessionMembers.model.Role;
import com.spring.techpractica.UI.Rest.Resources.User.UserResources;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
public class SessionMemberResources {

    private final UUID id;
    private final UserResources user;
    private final Role role;

    public SessionMemberResources(SessionMember sessionMember) {
        this.id = sessionMember.getUser().getId();
        this.user = new UserResources(sessionMember.getUser());
        this.role = sessionMember.getRole();
    }
}
