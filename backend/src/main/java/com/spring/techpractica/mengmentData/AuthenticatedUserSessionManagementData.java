package com.spring.techpractica.mengmentData;

import com.spring.techpractica.model.SessionRole;
import com.spring.techpractica.model.entity.AuthenticatedUserSession;
import com.spring.techpractica.model.entity.Session;
import com.spring.techpractica.model.entity.User;
import com.spring.techpractica.repository.AuthenticatedUserSessioneRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthenticatedUserSessionManagementData {

    private final AuthenticatedUserSessioneRepository
            authenticatedUserSessioneRepository;


    public List<Session> getUserSessionsByPageable(User user, Pageable pageable) {
        List<AuthenticatedUserSession> authenticatedUserSessions =
                authenticatedUserSessioneRepository.findAllByUser(user, pageable).getContent();
        return authenticatedUserSessions.stream().map
                (AuthenticatedUserSession::getSession).collect(Collectors.toList());
    }


    public long getNumberOfUserSessions(User user) {
        return authenticatedUserSessioneRepository.countByUser(user);

    }

    public AuthenticatedUserSession getUserBySessionIdAndRole(SessionRole role, long sessionId) {
        return authenticatedUserSessioneRepository.getAuthenticatedUserSessionByScopedRoleAndUserSessionIdSessionId(role, sessionId);
    }


    public Optional<AuthenticatedUserSession> findByUserUserIdAndUserSessionId(Long userId, Long sessionId) {
        return authenticatedUserSessioneRepository.findByUserUserIdAndSessionSessionId(userId, sessionId);
    }

}




