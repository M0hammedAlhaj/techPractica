package com.spring.techpractica.Core.Session;

import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.Shared.BaseRepository;
import com.spring.techpractica.Core.System.Entity.System;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SessionRepository extends BaseRepository<Session> {
    List<Session> exploreSessions(Pageable pageable);

    List<Session> getSessionsBySystems(List<System> systems, Pageable pageable);

}