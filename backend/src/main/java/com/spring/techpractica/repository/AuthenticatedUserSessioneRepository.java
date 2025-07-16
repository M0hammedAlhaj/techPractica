package com.spring.techpractica.repository;

import com.spring.techpractica.model.SessionRole;
import com.spring.techpractica.model.UserSessionId;
import com.spring.techpractica.model.entity.AuthenticatedUserSession;
import com.spring.techpractica.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthenticatedUserSessioneRepository extends JpaRepository<AuthenticatedUserSession, UserSessionId> {

    Page<AuthenticatedUserSession> findAllByUser(User user, Pageable pageable);

    long countByUser(User user);

    Optional<AuthenticatedUserSession> findByUserUserIdAndSessionSessionId(Long userId, Long sessionId);

    AuthenticatedUserSession getAuthenticatedUserSessionByScopedRoleAndUserSessionIdSessionId(SessionRole role,
                                                                          long sessionId);
}
