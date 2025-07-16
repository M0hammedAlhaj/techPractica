package com.spring.techpractica.maper;

import com.spring.techpractica.dto.session.SessionRequest;
import com.spring.techpractica.dto.session.SessionResponse;
import com.spring.techpractica.dto.session.SessionsResponse;
import com.spring.techpractica.mengmentData.AuthenticatedUserSessionManagementData;
import com.spring.techpractica.model.SessionRole;
import com.spring.techpractica.model.entity.AuthenticatedUserSession;
import com.spring.techpractica.model.entity.Session;
import com.spring.techpractica.model.entity.User;
import com.spring.techpractica.model.entity.techSkills.Category;
import com.spring.techpractica.model.entity.techSkills.Technology;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SessionMapper {



    public static Session sessionCreatorToSession(SessionRequest sessionRequest) {

        return Session.builder()
                .sessionName(sessionRequest.getNameSession())
                .sessionDescription(sessionRequest.getDescriptionSession())
                .isPrivate(sessionRequest.isPrivateSession())
                .sessionCategories(new ArrayList<>())
                .sessionRequests(new ArrayList<>())
                .sessionRequirements(new ArrayList<>())
                .sessionMembers(new ArrayList<>())
                .timestampList(new ArrayList<>())
                .sessionTechnologies(new ArrayList<>())
                .sessionCategories(new ArrayList<>())
                .sessionSystems(new ArrayList<>())
                .build();
    }

    public static SessionResponse sessionToSessionResponse(Session session) {
        User userOwner = session.getSessionMembers()
                .stream()
                .filter(authenticatedUserSession ->
                        authenticatedUserSession.getScopedRole()
                        .equals(SessionRole.OWNER))
                .map(AuthenticatedUserSession::getUser)
                .findFirst().get();

        return SessionResponse.
                builder()
                .id(session.getSessionId())
                .sessionName(session.getSessionName())
                .privateSession(session.isPrivate())
                .sessionDescription(session.getSessionDescription())
                .system(session.getSessionSystems().getFirst().getSystemName())
                .technologies(session.getSessionTechnologies().stream()
                        .map(Technology::getTechnologyName).toList())
                .categories(session.getSessionCategories()
                        .stream()
                        .map(Category::getCategoryName)
                        .toList())
                .ownerName(userOwner.getUserName())
                .build();
    }

    public static List<SessionResponse> sessionsToSessionResponses(List<Session> sessions) {
        return sessions.
                stream()
                .map(SessionMapper::sessionToSessionResponse)
                .toList();
    }

    public static SessionsResponse sessionsAndTotalSessionsToSessionsResponses(List<Session> sessions,
                                                                               long totalSessions) {
        List<SessionResponse> sessionResponse = sessionsToSessionResponses(sessions);
        return SessionsResponse.builder()
                .sessionsCount(totalSessions)
                .sessions(sessionResponse)
                .build();
    }
}
