package com.spring.techpractica.mengmentData;

import com.spring.techpractica.exception.ResourcesNotFoundException;
import com.spring.techpractica.model.entity.Session;
import com.spring.techpractica.model.entity.techSkills.System;
import com.spring.techpractica.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing session data operations such as saving, deleting,
 * and retrieving sessions by ID, category, or pagination.
 */
@Service
public class SessionManagementData {

    private final SessionRepository sessionRepository;

    /**
     * Constructor to inject the {@link SessionRepository} dependency.
     *
     * @param sessionRepository The repository used for session data operations.
     */
    @Autowired
    public SessionManagementData(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    /**
     * Saves the provided session entity to the database.
     *
     * @param session The session entity to be saved.
     * @return The saved session entity.
     */
    public Session saveSession(Session session) {
        return sessionRepository.save(session);
    }

    /**
     * Deletes the provided session entity from the database.
     *
     * @param session The session entity to be deleted.
     */
    public void deleteSession(Session session) {
        sessionRepository.delete(session);
    }

    /**
     * Retrieves a session by its ID. If the session is not found, it throws
     * a {@link ResourcesNotFoundException}.
     *
     * @param id The ID of the session to retrieve.
     * @return The session entity associated with the provided ID.
     * @throws ResourcesNotFoundException If no session is found with the provided ID.
     */
    public Session getSessionById(Long id) {
        return sessionRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourcesNotFoundException("Session not found with ID: " + id));

    }


    /**
     * Retrieves a session by its ID. This method returns an {@link Optional}
     * that may be empty if no session is found.
     *
     * @param id The ID of the session to retrieve.
     * @return An {@link Optional} containing the session if found, or empty if not.
     */
    public Optional<Session> findSessionById(Long id) {
        return sessionRepository.findById(id);
    }

    /**
     * Retrieves a paginated list of sessions associated with the given category.
     *
     * @param system   The category used to filter sessions.
     * @param pageable The pagination information.
     * @return A list of sessions that belong to the specified category and page.
     */
    public List<Session> getSessionsBySystemAndPageable(System system,
                                                        Pageable pageable) {
        Page<Session> page = sessionRepository.findAllBySessionSystems(system, pageable);
        return page.getContent();
    }

    /**
     * Retrieves a paginated list of all sessions.
     *
     * @param pageable The pagination information.
     * @return A list of sessions for the specified page.
     */
    public List<Session> getSessionsByPageable(Pageable pageable) {
        return sessionRepository.findAll(pageable).getContent();
    }

    public long getNumberOfSystemSessions(System system) {
        List<System> categories = new ArrayList<>();
        categories.add(system);
        return sessionRepository.countBySessionSystems(categories);
    }

    public long getNumberOfSessions() {
        return sessionRepository.count();
    }
}

