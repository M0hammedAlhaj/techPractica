package com.spring.techpractica.Core.SessionMembers;

import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.SessionMembers.Entity.SessionMember;
import com.spring.techpractica.Core.SessionMembers.model.Role;
import com.spring.techpractica.Core.SessionMembers.model.UserSessionId;
import com.spring.techpractica.Core.User.User;
import org.springframework.stereotype.Component;

@Component
public class SessionMembersFactory {

    public SessionMember create(Session session, User owner, Role role) {
        return SessionMember.builder()
                .userSessionId(new UserSessionId(session.getId(), owner.getId()))
                .user(owner)
                .session(session)
                .role(role)
                .build();
    }
}
