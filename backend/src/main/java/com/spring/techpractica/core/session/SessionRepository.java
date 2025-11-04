package com.spring.techpractica.core.session;

import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.shared.BaseRepository;
import com.spring.techpractica.core.system.entity.System;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface SessionRepository extends BaseRepository<Session> {
    List<Session> exploreSessions(Pageable pageable);

    List<Session> getSessionsBySystems(List<System> systems, Pageable pageable);

    List<Session> getSessionsByFieldId(UUID fieldId);

    List<Session> getSessionsBySystemId(UUID systemId);

    List<Session> getSessionsByTechnologyId(UUID technologyId);

    List<Session> findAllWithSpecification(Specification<Session> specification, Pageable pageable);

    Page<Session> getSessionsByUser(UUID userID, Pageable pageable);

    List<Request> getRequestsBySessionId(UUID sessionID);

    long getSessionsCount();

    long getUserSessionsCount(UUID userID);
}