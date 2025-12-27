package com.spring.techpractica.ui.rest.resources.session.member;

import com.spring.techpractica.core.session.members.Entity.SessionMember;
import com.spring.techpractica.core.session.members.model.Role;
import com.spring.techpractica.ui.rest.resources.user.UserResources;
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
