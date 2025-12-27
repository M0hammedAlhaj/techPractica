package com.spring.techpractica.application.user.auth.active.account;

import com.spring.techpractica.core.shared.Exception.OperationDuplicateException;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ActiveAccountUseCase {
    private UserRepository userRepository;

    public User execute(ActiveAccountCommand activeAccountCommand) {
        UUID id = activeAccountCommand.id();
        User user = userRepository.getOrThrowByID(activeAccountCommand.id());

        if (user.isInactiveAccount()) {
            user.activate();
            userRepository.update(user);
            return user;
        }
        throw new OperationDuplicateException("account already activate ");
    }
}