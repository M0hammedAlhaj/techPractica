package com.spring.techpractica.application.admin.manage.delete.user;

import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteUserUseCase {

    private final UserRepository userRepository;
    private final SessionMemberJpa sessionMemberJpa;

    public String execute(DeleteUserCommand command) {
        User user = userRepository.getOrThrowByID(command.userId());
        sessionMemberJpa.deleteByUserId(user.getId());
        userRepository.delete(user);

        return String.format("User %s has been deleted", user.getId());
    }
}
