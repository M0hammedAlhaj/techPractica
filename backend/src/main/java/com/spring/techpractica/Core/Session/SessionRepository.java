package com.spring.techpractica.Core.Session;

import com.spring.techpractica.Core.Request.Entity.Request;
import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.Shared.BaseRepository;
import com.spring.techpractica.Core.System.Entity.System;
import com.spring.techpractica.Core.User.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface SessionRepository extends BaseRepository<Session> {
    List<Session> exploreSessions(Pageable pageable);

    List<Session> getSessionsBySystems(List<System> systems, Pageable pageable);

    List<Session> findAllWithSpecification(Specification<Session> specification, Pageable pageable);

    Page<Session> getSessionsByUser(UUID userID, Pageable pageable);

    List<Request> getRequestsBySession(UUID sessionID);

    long getSessionsCount();

    long getUserSessionsCount(UUID userID);
}