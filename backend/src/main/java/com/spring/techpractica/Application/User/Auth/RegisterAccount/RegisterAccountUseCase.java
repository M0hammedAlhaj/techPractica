package com.spring.techpractica.Application.User.Auth.RegisterAccount;

import com.spring.techpractica.Core.User.Event.UserRegistrationEvent;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.User.UserFactory;
import com.spring.techpractica.Core.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RegisterAccountUseCase {
    private final UserFactory userFactory;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public User execute(RegisterAccountCommand command) {
        String email = command.email();
        if (userRepository.existsByEmail(email)) {
            return userFactory.create(command.name(), email, command.password());
        }
        User user = userFactory.create(command.name(), email, command.password());
        User userSaved = userRepository.save(user);
        publisher.publishEvent(new UserRegistrationEvent(userSaved.getId(), userSaved.getEmail(), userSaved.getName()));
        return userSaved;
    }
}
