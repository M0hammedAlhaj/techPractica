package com.spring.techpractica.infrastructure.Jpa.Session;

import com.spring.techpractica.Core.Request.Entity.Request;
import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.Session.SessionRepository;
import com.spring.techpractica.Core.Session.SessionStatus;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.System.Entity.System;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaSessionRepository implements SessionRepository {

    private final JpaSession jpaSession;

    @Override
    public Session save(Session session) {
        return jpaSession.save(session);
    }

    @Override
    public Session update(Session session) {
        UUID sessionId = session.getId();
        if (jpaSession.existsById(sessionId)) {
            jpaSession.save(session);
        }
        throw new ResourcesNotFoundException(sessionId);
    }

    @Override
    public Optional<Session> findById(UUID id) {
        return jpaSession.findById(id);
    }

    @Override
    public List<Session> exploreSessions(Pageable pageable) {
        return jpaSession.findAllByStatusNotIn(List.of(SessionStatus.DELETED, SessionStatus.ENDED), pageable).getContent();

    }

    @Override
    public List<Session> getSessionsBySystems(List<System> systems, Pageable pageable) {
        return jpaSession.findAllBySystemsAndStatusNotIn(systems,List.of(SessionStatus.DELETED, SessionStatus.ENDED), pageable).getContent();
    }

    @Override
    public List<Session> findAllWithSpecification(Specification<Session> specification, Pageable pageable) {
        return jpaSession.findAll(specification, pageable).getContent();
    }

    public Page<Session> getSessionsByUser(UUID userId, Pageable pageable) {
        return jpaSession.findAllByUserIdAndStatusNotIn(userId,List.of(SessionStatus.DELETED), pageable);
    }

    @Override
    public List<Request> getRequestsBySession(UUID sessionID) {
        return jpaSession.getRequestsBySession(sessionID);
    }

    @Override
    public long getSessionsCount() {
        return jpaSession.getAllSessionsCount(List.of(SessionStatus.DELETED));
    }

    @Override
    public long getUserSessionsCount(UUID userID) {
        return jpaSession.getAllSessionsCountByUser(List.of(SessionStatus.DELETED),userID);
    }
}