package com.spring.techpractica.application.session.explore;

import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExploreSessionsUseCase {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<Session> execute(ExploreSessionsCommand command) {

        Optional<UUID> uuidOptional = command.userId();

        if (uuidOptional.isEmpty()) {
            return sessionRepository.exploreSessions(PageRequest.of(command.page(),
                    command.size(),
                    Sort.by(Sort.Direction.DESC, "atCreated")));
        }

        UUID userId = uuidOptional.get();
        User user = userRepository.getOrThrowByID(userId);

//        if (user.isProfileComplete()) {
//            throw new UnsupportedOperationException(
//                    "Session exploration for users with completed profiles is not implemented yet"
//            );
//        }
        return sessionRepository.exploreSessions(PageRequest.of(command.page(), command.size())).stream()
                .filter( s -> !s.isOwner(userId) )
                .collect(Collectors.toList());
    }
}