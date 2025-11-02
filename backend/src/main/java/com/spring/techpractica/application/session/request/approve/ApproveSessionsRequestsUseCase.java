package com.spring.techpractica.application.session.request.approve;

import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.core.request.RequestRepository;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.session.members.Entity.SessionMember;
import com.spring.techpractica.core.session.members.SessionMembersFactory;
import com.spring.techpractica.core.session.members.model.Role;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.user.exception.UserAuthenticationException;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ApproveSessionsRequestsUseCase {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final RequestRepository requestRepository;
    private final SessionMembersFactory sessionMembersFactory;

    @Transactional
    public Request execute(ApproveSessionsRequestsCommand command) {

        User owner = userRepository.findById(command.ownerId())
                .orElseThrow(() -> new ResourcesNotFoundException(command.ownerId()));

        Session session = sessionRepository.findById(command.sessionId())
                .orElseThrow(() -> new ResourcesNotFoundException(command.sessionId()));


        if (!session.isOwner(owner.getId())) {
            throw new UserAuthenticationException("You are not owner of this session");
        }

        Request request = requestRepository.findByIdAndSessionId(command.requestId(), command.sessionId())
                .orElseThrow(() -> new ResourcesNotFoundException(command.requestId()));

        User user = request.getUser();

        request.approve();

        SessionMember sessionMember = sessionMembersFactory.create(session, user, Role.PARTICIPATE);

        session.addMember(sessionMember);
        sessionRepository.save(session);
        return requestRepository.save(request);
    }
}
