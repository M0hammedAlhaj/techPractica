package com.spring.techpractica.Application.User.Auth.ChangePassword;

import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.User.Service.UserService;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ChangePasswordUseCase {
    private final UserRepository userRepository;

    private final UserService userService;

    @Transactional
    public User execute(ChangePasswordCommand command) {
        User user = userRepository.getOrThrowByID(command.id());

        userService.changePassword(user, command.password());
        userRepository.update(user);
        return user;
    }
}
