package com.spring.techpractica.Application.Session.ExploreSession;

import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.Session.SessionRepository;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ExploreSessionsUseCase {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<Session> execute(ExploreSessionsCommand command) {

        Optional<UUID> uuidOptional = command.userId();

        if (uuidOptional.isEmpty()) {
            return sessionRepository.exploreSessions(PageRequest.of(command.page(), command.size()));
        }

        UUID userId = uuidOptional.get();
        User user = userRepository.getOrThrowByID(userId);

        if (user.isProfileComplete()) {
            throw new UnsupportedOperationException(
                    "Session exploration for users with completed profiles is not implemented yet"
            );
        }

        return sessionRepository.exploreSessions(PageRequest.of(command.page(), command.size()));
    }
}
