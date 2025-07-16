package com.spring.techpractica.service;

import com.spring.techpractica.mengmentData.AuthenticatedUserSessionManagementData;
import com.spring.techpractica.model.entity.AuthenticatedUserSession;
import com.spring.techpractica.model.entity.Session;
import com.spring.techpractica.model.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticatedUserSessionService {

    private final AuthenticatedUserSessionManagementData authenticatedUserSessionManagementData;


    public Optional<AuthenticatedUserSession> getAuthenticatedUserSession(User user, Session session) {
        return authenticatedUserSessionManagementData
                .findByUserUserIdAndUserSessionId(user.getUserId(), session.getSessionId());
    }


}
