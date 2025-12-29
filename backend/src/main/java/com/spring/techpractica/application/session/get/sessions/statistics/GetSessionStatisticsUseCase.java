package com.spring.techpractica.application.session.get.sessions.statistics;


import com.spring.techpractica.core.session.SessionStatisticsRepository;
import com.spring.techpractica.core.session.entity.statistics.SessionStatistics;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetSessionStatisticsUseCase {

    private final SessionStatisticsRepository  sessionStatisticsRepository;

    public SessionStatistics execute(GetSessionStatisticsCommand command) {
        return sessionStatisticsRepository.findBySessionId(command.sessionId());
    }
}