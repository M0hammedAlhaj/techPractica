package com.spring.techpractica.service.session.updateSession;


import com.spring.techpractica.dto.SessionAuthorization;
import com.spring.techpractica.dto.session.SessionRequest;
import com.spring.techpractica.dto.session.SessionResponse;
import com.spring.techpractica.exception.AuthenticationException;
import com.spring.techpractica.factory.RequirementFactory;
import com.spring.techpractica.maper.CategoryMapper;
import com.spring.techpractica.maper.RequirementMapper;
import com.spring.techpractica.maper.SessionMapper;
import com.spring.techpractica.mengmentData.*;
import com.spring.techpractica.model.SessionRole;
import com.spring.techpractica.model.entity.AuthenticatedUserSession;
import com.spring.techpractica.model.entity.Requirement;
import com.spring.techpractica.model.entity.Session;
import com.spring.techpractica.model.entity.User;
import com.spring.techpractica.service.session.createSession.SessionCategoryLinker;
import com.spring.techpractica.service.session.createSession.SessionFieldLinker;
import com.spring.techpractica.service.session.createSession.SessionTechnologyLinker;
import com.spring.techpractica.validator.SessionAuthorizationValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UpdateSessionService {

    private final SessionManagementData sessionManagementData;

    private final UserManagementData userManagementData;

    private final FieldManagementData fieldManagementData;

    private final AuthenticatedUserSessionManagementData authenticatedUserSessionManagementData;

    private final SessionTechnologyLinker technologyLinker;

    private final SessionCategoryLinker categoryLinker;

    private final SessionFieldLinker fieldLinker;

    private final RequirementMapper requirementMapper;

    private final SessionAuthorizationValidator sessionAuthorizationValidator;

    @Transactional
    public SessionResponse updateSession(Long sessionId,
                                         SessionRequest updatedSessionRequest,
                                         String userEmail) {


        User user = userManagementData.getUserByEmail(userEmail);
        Session session = sessionManagementData.getSessionById(sessionId);

        sessionAuthorizationValidator.
                validate(sessionAuthorizationBuilder
                        (authenticatedUserSessionManagementData.getByUserUserIdAndUserSessionId(user.getUserId(), sessionId)));


        session.setSessionName(updatedSessionRequest.getNameSession());

        session.setSessionDescription(updatedSessionRequest.getDescriptionSession());

        session.setPrivate(updatedSessionRequest.isPrivateSession());

        session.getSessionTechnologies().clear();
        technologyLinker.linkTechnologiesToSession(session, updatedSessionRequest.getTechnologies());

        session.getSessionCategories().clear();
        categoryLinker.linkCategoryToSession(session, updatedSessionRequest.getCategory());


        session.getSessionRequirements().clear();
        session.getSessionFields().clear();
        fieldLinker.linkFieldsToSession(session, updatedSessionRequest.getFields());

        sessionManagementData.saveSession(session);

        return SessionMapper.sessionToSessionResponse(session);
    }

    private SessionAuthorization sessionAuthorizationBuilder(AuthenticatedUserSession authenticatedUserSession) {
        return SessionAuthorization.builder()
                .sessionRole(SessionRole.OWNER)
                .authenticatedUserSession(authenticatedUserSession)
                .build();
    }

}
