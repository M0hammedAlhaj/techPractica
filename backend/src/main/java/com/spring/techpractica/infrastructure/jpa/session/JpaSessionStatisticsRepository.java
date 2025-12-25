package com.spring.techpractica.infrastructure.jpa.session;

import com.spring.techpractica.core.session.SessionStatisticsRepository;
import com.spring.techpractica.core.session.entity.statistics.SessionStatistics;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaSessionStatisticsRepository implements SessionStatisticsRepository {

    private final JpaSessionStatistics jpaSessionStatistics;

    @Override
    public SessionStatistics save(SessionStatistics sessionStatistics) {
        return jpaSessionStatistics.save(sessionStatistics);
    }

    @Override
    public SessionStatistics update(SessionStatistics sessionStatistics) {
        return null;
    }

    @Override
    public Optional<SessionStatistics> findById(UUID id) {
        return Optional.empty();
    }
}
