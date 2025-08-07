package com.spring.techpractica.Application.User.LoginAccount;

import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.User.Exception.UserAuthenticationException;
import com.spring.techpractica.Core.User.Service.PasswordEncryptor;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginAccountUseCase {
    private final UserRepository userRepository;
    private final PasswordEncryptor passwordEncryptor;

    public User execute(LoginAccountCommand command) {
        String email = command.email();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourcesNotFoundException(String.format("User not found By %s ", email)));

        if (passwordEncryptor.matches(command.password(), user.getPassword())) {
            return user;
        }
        throw new UserAuthenticationException("Invalid email or password");
    }
}
