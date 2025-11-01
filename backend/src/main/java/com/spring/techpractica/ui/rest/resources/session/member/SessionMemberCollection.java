package com.spring.techpractica.ui.rest.resources.session.member;

import com.spring.techpractica.core.session.members.Entity.SessionMember;
import lombok.Getter;

import java.util.List;

@Getter
public class SessionMemberCollection {
    private final List<SessionMemberResources> members;

    public SessionMemberCollection(List<SessionMember> members) {
        this.members = members.stream().map(
                SessionMemberResources::new
        ).toList();
    }
}
