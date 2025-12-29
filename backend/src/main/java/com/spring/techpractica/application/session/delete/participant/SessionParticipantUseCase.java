package com.spring.techpractica.application.session.delete.participant;

import com.spring.techpractica.core.request.RequestRepository;
import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.shared.Exception.UnauthorizedActionException;
import com.spring.techpractica.core.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SessionParticipantUseCase {

    private final SessionRepository sessionRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    @Transactional
    public void execute(DeleteSessionParticipantCommand command) {
        Session session = sessionRepository.getOrThrowByID(command.sessionId());

        if (!session.isOwner(command.ownerId())) {
            throw new UnauthorizedActionException("User must be the session owner to delete participant");
        }

        if (command.ownerId().equals(command.participantId())) {
            throw new UnauthorizedActionException("Owner cannot be removed from session");
        }

        Request request = requestRepository.getOrThrowByID(command.requestId());

        request.delete();

        requestRepository.save(request);

        session.removeMember(command.participantId());

        sessionRepository.save(session);
    }
}