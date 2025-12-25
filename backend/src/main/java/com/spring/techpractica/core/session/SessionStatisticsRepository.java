package com.spring.techpractica.core.session;

import com.spring.techpractica.core.session.entity.statistics.SessionStatistics;
import com.spring.techpractica.core.shared.BaseRepository;

import java.util.UUID;

public interface SessionStatisticsRepository extends BaseRepository<SessionStatistics> {

    SessionStatistics findBySessionId(UUID sessionId);
}
