package com.spring.techpractica.application.session.delete.participant;

import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.shared.Exception.UnauthorizedActionException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SessionParticipantUseCase {

    private final SessionRepository sessionRepository;

    public void execute(DeleteSessionParticipantCommand command) {
        Session session = sessionRepository.getOrThrowByID(command.sessionId());

        if (!session.isOwner(command.ownerId())) {
            throw new UnauthorizedActionException("User must be the session owner to delete participant");
        }

        if (command.ownerId().equals(command.participantId())) {
            throw new UnauthorizedActionException("Owner cannot be removed from session");
        }

        session.removeMember(command.participantId());

        sessionRepository.save(session);
    }
}