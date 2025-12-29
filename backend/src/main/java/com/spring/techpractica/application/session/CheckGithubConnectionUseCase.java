package com.spring.techpractica.application.session;

import com.spring.techpractica.core.shared.Exception.UnauthorizedActionException;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CheckGithubConnectionUseCase {

    private final UserRepository userRepository;

    public String execute(UUID userId) {
        User user = userRepository.getOrThrowByID(userId);

        if (user.getGithubEmail() == null) {
            throw new UnauthorizedActionException("User does not connect to Github");
        }

        return "connected";
    }
}