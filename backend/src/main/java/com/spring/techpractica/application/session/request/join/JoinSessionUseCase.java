package com.spring.techpractica.application.session.request.join;

import com.spring.techpractica.application.notification.CreateNotificationUseCase;
import com.spring.techpractica.core.notification.NotificationRepository;
import com.spring.techpractica.core.notification.entity.Notification;
import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.event.UserAcceptedToSessionEvent;
import com.spring.techpractica.core.session.members.Entity.SessionMember;
import com.spring.techpractica.core.session.members.SessionMembersFactory;
import com.spring.techpractica.core.session.members.model.Role;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class JoinSessionUseCase {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final SessionMembersFactory sessionMembersFactory;
    private final CreateNotificationUseCase createNotificationUseCase;
    private final ApplicationEventPublisher eventPublisher;
    private final NotificationRepository notificationRepository;

    @Transactional
    public Session execute(JoinSessionCommand command) {

        User user = userRepository.getOrThrowByID(command.userId());
        Session session = sessionRepository.getOrThrowByID(command.sessionId());


        Notification notification;
        String title = "Session Join";
        String content;



            if (!session.isMember(user.getId())) {
                SessionMember sessionMember = sessionMembersFactory.create(session, user, Role.PARTICIPATE);
                session.addMember(sessionMember);
            }


            sessionRepository.save(session);

            content = "Congratulations! You have been joined to the session: " + session.getName();


            notification = createNotificationUseCase.execute(user, title, content);

            notificationRepository.save(notification);


                eventPublisher.publishEvent(new UserAcceptedToSessionEvent(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        session.getId(),
                        session.getName()
                ));
        return session;
    }
}