package com.spring.techpractica.service.session;

import com.spring.techpractica.dto.UserRequestSession;
import com.spring.techpractica.dto.session.SessionRequest;
import com.spring.techpractica.dto.session.SessionRequestCreation;
import com.spring.techpractica.dto.session.SessionResponse;
import com.spring.techpractica.dto.session.SessionsResponse;
import com.spring.techpractica.exception.AuthenticationException;
import com.spring.techpractica.factory.PageRequestFactory;
import com.spring.techpractica.factory.RequestBuilding;
import com.spring.techpractica.maper.SessionMapper;
import com.spring.techpractica.mengmentData.*;
import com.spring.techpractica.model.SessionRole;
import com.spring.techpractica.model.entity.*;
import com.spring.techpractica.model.entity.techSkills.System;
import com.spring.techpractica.service.session.createSession.CreateSessionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SessionService {

    private final RequirementManagementData requirementManagementData;

    private final SessionManagementData sessionManagementData;

    private final UserManagementData userManagementData;

    private final SystemManagementData systemManagementData;

    private final CreateSessionService createSessionService;

    private final TechnologyManagementData technologyManagementData;

    private final CategoryManagementData categoryManagementData;

    private final AuthenticatedUserSessionManagementData authenticatedUserSessionManagementData;


    public SessionResponse createSession(SessionRequest sessionRequest,
                                         String userEmail) {

        return createSessionService.createSession(sessionRequest,
                userEmail);
    }


    public SessionsResponse getSessionsByUserEmail(String userEmail,
                                                   int pageSize,
                                                   int pageNumber) {

        User user = userManagementData.getUserByEmail(userEmail);

        if (user.getUserTechnologies() == null || user.getUserTechnologies().isEmpty()) {

            List<Session> sessions = sessionManagementData.getSessionsByPageable(
                    PageRequestFactory.createPageRequest(pageSize, pageNumber));

            long totalSession = sessionManagementData.getNumberOfSessions();

            return SessionMapper.sessionsAndTotalSessionsToSessionsResponses(sessions, sessionManagementData.getNumberOfSessions());
        }
        return null;
    }

    public SessionsResponse getSessionsBySystemName(String systemName, int pageSize, int pageNumber) {

        System system = systemManagementData.getSystemByName(systemName);

        List<Session> sessions = sessionManagementData
                .getSessionsBySystemAndPageable(system,
                        PageRequestFactory.createPageRequest(pageSize, pageNumber));

        long totalSession = sessionManagementData.getNumberOfSystemSessions(system);



        return SessionMapper.sessionsAndTotalSessionsToSessionsResponses(sessions, totalSession);
    }

    public SessionsResponse getUserSessions(String userEmail, int pageSize, int pageNumber) {
        User user = userManagementData.getUserByEmail(userEmail);

        List<Session> sessions = authenticatedUserSessionManagementData.getUserSessionsByPageable
                (user, PageRequestFactory.createPageRequest(pageSize, pageNumber));

        long totalSession = authenticatedUserSessionManagementData.getNumberOfUserSessions(user);


        return SessionMapper.sessionsAndTotalSessionsToSessionsResponses(sessions, totalSession);
    }

    @Transactional
    public void deleteSessionByUserEmailAndSessionId(String username
            , Long sessionId) {

        Session session = sessionManagementData.getSessionById(sessionId);
        User user = userManagementData.getUserByEmail(username);

        if (!getUserRole(user.getUserId(), sessionId).equals(SessionRole.OWNER)) {
            throw new AuthenticationException("User dont have authorization");
        }

        sessionManagementData.deleteSession(session);
    }

    @Transactional
    public SessionResponse updateSession(Long sessionId,
                                         SessionRequest updatedSessionRequest,
                                         String userEmail) {


        User user = userManagementData.getUserByEmail(userEmail);

        Session session = sessionManagementData.getSessionById(sessionId);


        if (!getUserRole(user.getUserId(), sessionId).equals(SessionRole.OWNER)) {
            throw new AuthenticationException("User dont have authorization");
        }


        session.setSessionName(updatedSessionRequest.getNameSession());

        session.setSessionDescription(updatedSessionRequest.getDescriptionSession());

        session.setPrivate(updatedSessionRequest.isPrivateSession());

        session.setSessionTechnologies(
                new ArrayList<>(technologyManagementData
                        .getTechnologiesByTechnologiesName(updatedSessionRequest.getTechnologies()))
        );

        session.setSessionSystems(
                new ArrayList<>(categoriesStringToCategoriesList(
                        List.of(updatedSessionRequest.getSystem())
                ))
        );

//        session.getSessionRequirements().clear();
//
//        List<Requirement> requirements = updatedSessionRequest
//                .getCategories()
//                .stream()
//                .map(category -> RequirementFactory.createRequirement(
//                        session,
//                        categoryManagementData.getCategoryByCategoryName(category)
//                ))
//                .collect(Collectors.toCollection(ArrayList::new));

//        session.getSessionRequirements()
//                .addAll(requirements);

        session.setSessionCategories(
                new ArrayList<>(categoryManagementData
                        .getCategoriesByCategoriesName(updatedSessionRequest.getCategories()))
        );

        sessionManagementData.saveSession(session);

        return SessionMapper.sessionToSessionResponse(session);
    }

    private List<System> categoriesStringToCategoriesList(List<String> categories) {
        return categories.stream()
                .map(systemManagementData::getSystemByName)
                .toList();
    }

    public SessionRole getUserRole(Long userId, Long sessionId) {

        AuthenticatedUserSession authenticatedUserSession = authenticatedUserSessionManagementData
                .findByUserUserIdAndUserSessionId(userId, sessionId)
                .orElseThrow(() -> new AuthenticationException("User Don't have access in session"));

        return authenticatedUserSession.getScopedRole();

    }

    public SessionsResponse getSessions(int pageSize, int pageNumber) {
        List<Session> sessions = sessionManagementData.
                getSessionsByPageable(PageRequestFactory.createPageRequest(pageSize, pageNumber));

        return SessionMapper.sessionsAndTotalSessionsToSessionsResponses(sessions, sessionManagementData.getNumberOfSessions());
    }

    @Transactional
    public void createRequestSession(SessionRequestCreation sessionRequestCreation,
                                     String userEmail) {

        Session session =
                sessionManagementData.getSessionById(sessionRequestCreation.getSessionId());

        User user = userManagementData.getUserByEmail(userEmail);


        Request request = RequestBuilding.
                createRequestFrom(session, user, sessionRequestCreation.getBrief());


        Requirement requirement =
                requirementManagementData.
                        getRequirementBySessionIdAndSystem(session.getSessionId(), sessionRequestCreation.getCategoryName());

        request.setRequirement(requirement);

        session.getSessionRequests()
                .add(request);

        sessionManagementData.saveSession(session);
    }

    @Transactional
    public List<UserRequestSession> getSessionsRequest(Long sessionId, String username) {

        Session session = sessionManagementData.getSessionById(sessionId);
        User user = userManagementData.getUserByEmail(username);

        if (!getUserRole(user.getUserId(), sessionId).equals(SessionRole.OWNER)) {
            throw new AuthenticationException("User dont have authorization");
        }


        List<Request> requests = session.getSessionRequests();


        if (requests == null) {
            return new ArrayList<>();
        }

        return requests
                .stream()
                .filter((r) -> r.getRequirement() != null)
                .map(r -> UserRequestSession.builder()
                        .brief(r.getBrief())
                        .username(r.getUser().getUserName())
                        .categoryName(r.getRequirement()
                                .getCategory()
                                .getCategoryName())
                        .build())
                .collect(Collectors.toList());
    }
}