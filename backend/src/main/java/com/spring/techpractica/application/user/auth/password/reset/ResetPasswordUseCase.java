package com.spring.techpractica.application.user.auth.password.reset;

import com.spring.techpractica.core.user.event.ResetPasswordEvent;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ResetPasswordUseCase {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserRepository userRepository;

    public void execute(ResetPasswordCommand command) {
        String email = command.email();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return;
        }
        User user = optionalUser.get();
        applicationEventPublisher.publishEvent(new ResetPasswordEvent(user.getId(), user.getEmail(), user.getName()));
    }
}
