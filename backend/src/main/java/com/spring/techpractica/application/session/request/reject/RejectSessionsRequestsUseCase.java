package com.spring.techpractica.application.session.request.reject;

import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.core.request.RequestRepository;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.user.exception.UserAuthenticationException;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RejectSessionsRequestsUseCase {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final RequestRepository requestRepository;

    public Request execute(RejectSessionsRequestsCommand command) {

        User user = userRepository.findById(command.ownerId())
                .orElseThrow(() -> new ResourcesNotFoundException(command.ownerId()));

        Session session = sessionRepository.findById(command.sessionId())
                .orElseThrow(() -> new ResourcesNotFoundException(command.sessionId()));

        if (!session.isOwner(user.getId())) {
            throw new UserAuthenticationException("You are not owner of this session");
        }

        Request request = requestRepository.findByIdAndSessionId(command.requestId(), command.sessionId())
                .orElseThrow(() -> new ResourcesNotFoundException(command.requestId()));


        request.reject();

        return requestRepository.save(request);
    }
}
