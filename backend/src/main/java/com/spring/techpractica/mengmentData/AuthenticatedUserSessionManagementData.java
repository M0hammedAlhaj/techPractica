package com.spring.techpractica.mengmentData;

import com.spring.techpractica.exception.ResourcesNotFoundException;
import com.spring.techpractica.model.entity.AuthenticatedUserSession;
import com.spring.techpractica.model.entity.Session;
import com.spring.techpractica.model.entity.User;
import com.spring.techpractica.repository.AuthenticatedUserSessioneRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthenticatedUserSessionManagementData {

    private final AuthenticatedUserSessioneRepository
            authenticatedUserSessioneRepository;


    /*
    Get authentication specif users  by pages find
     */
    //

    public List<Session> getUserSessionsByPageable(User user, Pageable pageable) {
        List<AuthenticatedUserSession> authenticatedUserSessions =
                authenticatedUserSessioneRepository.findAllByUser(user, pageable).getContent();
        return authenticatedUserSessions.stream().map
                (AuthenticatedUserSession::getSession).collect(Collectors.toList());
    }
    //


//    private
//
    //fitch getAll count authtication specif users (COUNT)

    public long getNumberOfUserSessions(User user) {
        return authenticatedUserSessioneRepository.countByUser(user);

    }


    public Optional<AuthenticatedUserSession> findByUserUserIdAndUserSessionId(Long userId, Long sessionId) {
        return authenticatedUserSessioneRepository.findByUserUserIdAndSessionSessionId(userId, sessionId);
    }

    public AuthenticatedUserSession getByUserUserIdAndUserSessionId(Long userId, Long sessionId) {
        return authenticatedUserSessioneRepository.findByUserUserIdAndSessionSessionId(userId, sessionId)
                .orElseThrow(()-> new ResourcesNotFoundException("Authenticated User Session Not Found"));
    }

}




