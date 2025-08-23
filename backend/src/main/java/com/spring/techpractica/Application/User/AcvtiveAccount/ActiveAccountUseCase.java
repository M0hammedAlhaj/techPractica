package com.spring.techpractica.Application.User.AcvtiveAccount;

import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ActiveAccountUseCase {
    private UserRepository userRepository;

    public User execute(ActiveAccountCommand activeAccountCommand) {
        UUID id = activeAccountCommand.id();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException(id));

        user.activate();
        userRepository.update(user);
        return user;
    }
}
