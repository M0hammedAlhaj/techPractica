package com.spring.techpractica.application.session.request.reject;

import com.spring.techpractica.application.notification.CreateNotificationUseCase;
import com.spring.techpractica.core.notification.NotificationRepository;
import com.spring.techpractica.core.notification.entity.Notification;
import com.spring.techpractica.core.request.RequestRepository;
import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.shared.Exception.UserAlreadyMemberException;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import com.spring.techpractica.core.user.exception.UserAuthenticationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RejectSessionsRequestsUseCase {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final RequestRepository requestRepository;
    private final CreateNotificationUseCase createNotificationUseCase;
    private final NotificationRepository notificationRepository;

    @Transactional
    public Request execute(RejectSessionsRequestsCommand command) {

        User owner = userRepository.getOrThrowByID(command.ownerId());
        Session session = sessionRepository.getOrThrowByID(command.sessionId());

        if (!session.isOwner(owner.getId())) {
            throw new UserAuthenticationException("You are not owner of this session");
        }

        Request request = requestRepository.findByIdAndSessionId(command.requestId(), command.sessionId())
                .orElseThrow(() -> new ResourcesNotFoundException(command.requestId()));

        User user = request.getUser();

        Notification notification;
        String title = "Session Request Status";
        String content;

        if (!request.isRejected()) {
            request.reject();
            requestRepository.save(request);
            content = "Unfortunately, your request to join the session: " + session.getName() + " has been rejected.";
        }
        else {
           throw new UserAlreadyMemberException("You are already rejected on this session");
       }

        notification = createNotificationUseCase.execute(user, title, content);

        notificationRepository.save(notification);

        return request;
    }
}
