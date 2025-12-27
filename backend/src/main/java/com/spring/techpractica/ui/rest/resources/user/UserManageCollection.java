package com.spring.techpractica.ui.rest.resources.user;

import com.spring.techpractica.application.session.get.user.sessions.count.GetUserSessionsCountCommand;
import com.spring.techpractica.application.session.get.user.sessions.count.GetUserSessionsCountUseCase;
import com.spring.techpractica.core.user.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserManageCollection {

    private final List<UserManageResources> userCollection;

    public UserManageCollection(List<User> users, GetUserSessionsCountUseCase getUserSessionsCountUseCase) {
        this.userCollection = users.stream()
                .map(user -> new UserManageResources(user,getUserSessionsCountUseCase.execute(new GetUserSessionsCountCommand(user.getId()))))
                .collect(Collectors.toList());
    }
}