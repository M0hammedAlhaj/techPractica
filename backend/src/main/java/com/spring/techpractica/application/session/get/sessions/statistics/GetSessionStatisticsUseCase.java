package com.spring.techpractica.application.session.get.sessions.statistics;


import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.session.SessionStatisticsRepository;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.entity.statistics.SessionStatistics;
import com.spring.techpractica.core.shared.Exception.UnauthorizedActionException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetSessionStatisticsUseCase {

    private final SessionStatisticsRepository  sessionStatisticsRepository;
    private final SessionRepository  sessionRepository;

    public SessionStatistics execute(GetSessionStatisticsCommand command) {
        Session session = sessionRepository.getOrThrowByID(command.sessionId());

        if (!session.isMember(command.userId())) {
            throw new UnauthorizedActionException("You are not member on this session");
        }

        return sessionStatisticsRepository.findBySessionId(command.sessionId());
    }
}
