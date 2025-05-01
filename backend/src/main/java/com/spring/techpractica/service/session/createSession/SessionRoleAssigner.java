package com.spring.techpractica.service.session.createSession;

import com.spring.techpractica.factory.AuthenticatedUserSessionFactory;
import com.spring.techpractica.model.SessionRole;
import com.spring.techpractica.model.entity.Session;
import com.spring.techpractica.model.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SessionRoleAssigner {

    private final AuthenticatedUserSessionFactory authenticatedUserSessionFactory;

    public void assignOwner(Session session, User user) {

        session.getSessionMembers()
                .add(authenticatedUserSessionFactory.createAuthenticatedUserSession(session,
                        user, SessionRole.OWNER));
    }
}