package com.spring.techpractica.application.session.request.approve;

import com.spring.techpractica.application.notification.CreateNotificationUseCase;
import com.spring.techpractica.core.notification.NotificationRepository;
import com.spring.techpractica.core.notification.entity.Notification;
import com.spring.techpractica.core.request.RequestRepository;
import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.event.UserAcceptedToSessionEvent;
import com.spring.techpractica.core.session.members.Entity.SessionMember;
import com.spring.techpractica.core.session.members.SessionMembersFactory;
import com.spring.techpractica.core.session.members.model.Role;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.shared.Exception.UserAlreadyMemberException;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import com.spring.techpractica.core.user.exception.UserAuthenticationException;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ApproveSessionsRequestsUseCase {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final RequestRepository requestRepository;
    private final SessionMembersFactory sessionMembersFactory;
    private final CreateNotificationUseCase createNotificationUseCase;
    private final ApplicationEventPublisher eventPublisher;
    private final NotificationRepository notificationRepository;

    @Transactional
    public Request execute(ApproveSessionsRequestsCommand command) {

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

        if (!request.isApproved()) {

            if (!session.isMember(user.getId())) {
                SessionMember sessionMember = sessionMembersFactory.create(session, user, Role.PARTICIPATE);
                session.addMember(sessionMember);
            }

            request.approve();

            sessionRepository.save(session);
            requestRepository.save(request);

            content = "Congratulations! You have been accepted to the session: " + session.getName();
        } else {
            throw new UserAlreadyMemberException(user.getId(), session.getId());
        }

        notification = createNotificationUseCase.execute(user, title, content);

        notificationRepository.save(notification);

            if (!request.isApproved()) {
                eventPublisher.publishEvent(new UserAcceptedToSessionEvent(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        session.getId(),
                        session.getName()
                ));
            }
        return request;
    }
}