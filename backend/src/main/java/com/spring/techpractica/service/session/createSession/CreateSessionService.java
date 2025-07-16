package com.spring.techpractica.service.session.createSession;

import com.spring.techpractica.dto.session.SessionRequest;
import com.spring.techpractica.dto.session.SessionResponse;
import com.spring.techpractica.factory.SessionFactory;
import com.spring.techpractica.maper.SessionMapper;
import com.spring.techpractica.mengmentData.SessionManagementData;
import com.spring.techpractica.mengmentData.UserManagementData;
import com.spring.techpractica.model.entity.Session;
import com.spring.techpractica.model.entity.User;
import com.spring.techpractica.service.session.linker.SessionCategoryLinker;
import com.spring.techpractica.service.session.linker.SessionRoleAssigner;
import com.spring.techpractica.service.session.linker.SessionSystemLinker;
import com.spring.techpractica.service.session.linker.SessionTechnologyLinker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class responsible for handling the creation of a new {@link Session}.
 * <p>
 * This class orchestrates the process of:
 * <ul>
 *     <li>Retrieving the session creator (owner) from the user email</li>
 *     <li>Creating a new session instance from the request using {@link SessionFactory}</li>
 *     <li>Assigning the owner role to the user using {@link SessionRoleAssigner}</li>
 *     <li>Linking requirements (fields), category, and technologies to the session</li>
 *     <li>Saving the session to the database</li>
 *     <li>Returning the session data in a {@link SessionResponse} DTO</li>
 * </ul>
 * </p>
 */
@Service
@AllArgsConstructor
public class CreateSessionService {

    private final UserManagementData userManagementData;
    private final SessionFactory sessionFactory;
    private final SessionRoleAssigner roleAssigner;
    private final SessionCategoryLinker categoryLinker;
    private final SessionSystemLinker systemLinker;
    private final SessionTechnologyLinker technologyLinker;
    private final SessionManagementData sessionManagementData;

    /**
     * Creates a new session based on the data provided by the user and the session creation request.
     */
    @Transactional
    public SessionResponse createSession(SessionRequest request, String userEmail) {

        User owner = userManagementData.getUserByEmail(userEmail);
        Session session = sessionFactory.createFrom(request);

        roleAssigner.assignRole(session, owner);
        categoryLinker.linkCategoriesToSession(session, request.getCategories());

        systemLinker.linkSystemToSession(session, request.getSystem());

        technologyLinker.linkTechnologiesToSession(session, request.getTechnologies());

        session = sessionManagementData.saveSession(session);

        return SessionMapper.sessionToSessionResponse(session);
    }

}

