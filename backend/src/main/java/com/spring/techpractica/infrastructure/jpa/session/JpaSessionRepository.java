package com.spring.techpractica.infrastructure.jpa.session;

import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.session.SessionStatus;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.system.entity.System;
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
            return jpaSession.findAllByStatusNotInAndIsPrivateFalse(List.of(SessionStatus.DELETED, SessionStatus.ENDED), pageable).getContent();
        }

    @Override
    public List<Session> getSessionsBySystems(List<System> systems, Pageable pageable) {
        return jpaSession.findBySystemsInAndStatusNotInAndIsPrivateFalse(systems,List.of(SessionStatus.DELETED, SessionStatus.ENDED), pageable).getContent();
    }

    @Override
    public List<Session> getSessionsByFieldId(UUID fieldId) {
        return jpaSession.getSessionsByField_Id(fieldId, List.of(SessionStatus.DELETED, SessionStatus.ENDED));
    }

    @Override
    public List<Session> getSessionsBySystemId(UUID systemId) {
        return jpaSession.getSessionsBySystem_Id(systemId, List.of(SessionStatus.DELETED, SessionStatus.ENDED));
    }

    @Override
    public List<Session> getSessionsByTechnologyId(UUID technologyId) {
        return jpaSession.getSessionsByTechnology_Id(technologyId, List.of(SessionStatus.DELETED, SessionStatus.ENDED));

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