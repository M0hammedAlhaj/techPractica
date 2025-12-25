package com.spring.techpractica.infrastructure.jpa.session;

import com.spring.techpractica.core.session.entity.statistics.SessionStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaSessionStatistics extends JpaRepository<SessionStatistics, UUID> {
}
