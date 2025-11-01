package com.spring.techpractica.application.user.auth.login;

import com.spring.techpractica.core.user.exception.UserAuthenticationException;
import com.spring.techpractica.core.user.service.PasswordEncryptor;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginAccountUseCase {
    private final UserRepository userRepository;
    private final PasswordEncryptor passwordEncryptor;

    public User execute(LoginAccountCommand command) {
        String email = command.email();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncryptor.matches(command.password(), user.getPassword())) {
                return user;
            }
        }
        throw new UserAuthenticationException("Invalid email or password");
    }
}