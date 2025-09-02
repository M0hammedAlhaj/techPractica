package com.spring.techpractica.UI.Rest.Resources.SessionMember;

import com.spring.techpractica.Core.SessionMembers.Entity.SessionMember;
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
