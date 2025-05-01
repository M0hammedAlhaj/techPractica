package com.spring.techpractica.service.session;

import com.spring.techpractica.dto.session.SessionRequest;
import com.spring.techpractica.dto.session.SessionResponse;
import com.spring.techpractica.dto.session.SessionsResponse;
import com.spring.techpractica.exception.AuthenticationException;
import com.spring.techpractica.factory.PageRequestFactory;
import com.spring.techpractica.factory.RequirementFactory;
import com.spring.techpractica.maper.SessionMapper;
import com.spring.techpractica.mengmentData.*;
import com.spring.techpractica.model.SessionRole;
import com.spring.techpractica.model.entity.AuthenticatedUserSession;
import com.spring.techpractica.model.entity.Requirement;
import com.spring.techpractica.model.entity.Session;
import com.spring.techpractica.model.entity.User;
import com.spring.techpractica.model.entity.techSkills.Category;
import com.spring.techpractica.service.session.createSession.CreateSessionService;
import com.spring.techpractica.service.session.updateSession.UpdateSessionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SessionService {

    private final SessionManagementData sessionManagementData;

    private final UserManagementData userManagementData;

    private final CategoryManagementData categoryManagementData;

    private final CreateSessionService createSessionService;

    private final UpdateSessionService updateSessionService;

    private final TechnologyManagementData technologyManagementData;

    private final FieldManagementData fieldManagementData;

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

            return SessionMapper.sessionsAndTotalSessionsToSessionsResponses(sessions, totalSession);
        }
        return null;
    }

    public SessionsResponse getSessionsByCategoryName(String categoryName, int pageSize, int pageNumber) {

        Category category = categoryManagementData.getCategoryByName(categoryName);

        List<Session> sessions = sessionManagementData
                .getSessionsByCategoryAndPageable(category, PageRequestFactory.createPageRequest(pageSize, pageNumber));

        long totalSession = sessionManagementData.getNumberOfCategorySessions(category);

        return SessionMapper.sessionsAndTotalSessionsToSessionsResponses(sessions, totalSession);

    }

    public SessionsResponse getUserSessions(String userEmail, int pageSize, int pageNumber) {
        User user = userManagementData.getUserByEmail(userEmail);

        List<Session> sessions = authenticatedUserSessionManagementData.getUserSessionsByPageable
                (user, PageRequestFactory.createPageRequest(pageSize, pageNumber));

        long totalSession = authenticatedUserSessionManagementData.getNumberOfUserSessions(user);


        return SessionMapper.sessionsAndTotalSessionsToSessionsResponses(sessions, totalSession);
    }

    //
    @Transactional
    public void deleteSessionByUserEmailAndSessionId(String username
            , Long sessionId) {

        Session session = sessionManagementData.getSessionById(sessionId);
        sessionManagementData.deleteSession(session);

    }


    @Transactional
    public SessionResponse updateSession(Long sessionId,
                                         SessionRequest updatedSessionRequest,
                                         String userEmail) {

        return updateSessionService.updateSession(sessionId,updatedSessionRequest,userEmail);

    }
//    @Transactional
//    public SessionResponse updateSession(Long sessionId,
//                                         SessionRequest updatedSessionRequest,
//                                         String userEmail) {
//
//
//        User user = userManagementData.getUserByEmail(userEmail);
//
//        Session session = sessionManagementData.getSessionById(sessionId);
//
//        if (getSessionRole(user.getUserId(), sessionId) != SessionRole.OWNER) {
//            throw new AuthenticationException("User must be an OWNER to perform this action.");
//        }
//
//        session.setSessionName(updatedSessionRequest.getNameSession());
//
//        session.setSessionDescription(updatedSessionRequest.getDescriptionSession());
//
//        session.setPrivate(updatedSessionRequest.isPrivateSession());
//
//        session.setSessionTechnologies(
//                new ArrayList<>(technologyManagementData
//                        .getTechnologiesByTechnologiesName(updatedSessionRequest.getTechnologies()))
//        );
//
//        session.setSessionCategories(
//                new ArrayList<>(categoriesStringToCategoriesList(
//                        List.of(updatedSessionRequest.getCategory())
//                ))
//        );
//
//        session.getSessionRequirements().clear();
//
//        List<Requirement> requirements = updatedSessionRequest.getFields().stream()
//                .map(field -> RequirementFactory.createRequirement(
//                        session,
//                        fieldManagementData.getFieldByFieldName(field)
//                ))
//                .collect(Collectors.toCollection(ArrayList::new));
//
//        session.getSessionRequirements().addAll(requirements);
//
//        session.setSessionFields(
//                new ArrayList<>(fieldManagementData
//                        .getFieldsByFieldsName(updatedSessionRequest.getFields()))
//        );
//
//        sessionManagementData.saveSession(session);
//
//        return SessionMapper.sessionToSessionResponse(session);
//    }

//    private List<Category> categoriesStringToCategoriesList(List<String> categories) {
//        return categories.stream()
//                .map(categoryManagementData::getCategoryByName)
//                .toList();
//    }

//    public SessionRole getSessionRole(Long userId, Long sessionId) {
//
//        AuthenticatedUserSession authenticatedUserSession = authenticatedUserSessionManagementData
//                .findByUserUserIdAndUserSessionId(userId, sessionId)
//                .orElseThrow(() -> new AuthenticationException("User is not authenticated"));
//
//        return authenticatedUserSession.getScopedRole();
//
//    }

}