package com.spring.techpractica.application.admin.manage.delete.user;

import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteUserUseCase {

    private final UserRepository userRepository;

    public String execute(DeleteUserCommand command){
        User user = userRepository.getOrThrowByID(command.userId());

        user.setDeleted();

        userRepository.update(user);

        return String.format("User %s has been deleted", user.getId());
    }
}
