package com.spring.techpractica.core.session.members;

import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.members.Entity.SessionMember;
import com.spring.techpractica.core.session.members.model.Role;
import com.spring.techpractica.core.session.members.model.UserSessionId;
import com.spring.techpractica.core.user.User;
import org.springframework.stereotype.Component;

@Component
public class SessionMembersFactory {

    public SessionMember create(Session session, User owner, Role role) {
        return SessionMember.builder()
                .user(owner)
                .session(session)
                .role(role)
                .build();
    }
}
