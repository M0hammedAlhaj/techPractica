package com.spring.techpractica.application.user.profile.get;

import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class GetProfileUseCase {

    private final UserRepository userRepository;

    @Transactional
    public User execute(GetProfileCommand command) {
        User user = userRepository.getOrThrowByID(command.userId());

        if (!user.isProfileComplete()) {
            throw new UnsupportedOperationException(
                    "user profile is not implemented yet"
            );
        }

        return user;
    }
}
