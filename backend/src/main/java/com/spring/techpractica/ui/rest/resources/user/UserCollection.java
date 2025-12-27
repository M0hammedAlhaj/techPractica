package com.spring.techpractica.ui.rest.resources.user;

import com.spring.techpractica.core.user.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserCollection {

    private final List<UserResources> userCollection;

    public UserCollection(List<User> users) {
        this.userCollection = users.stream()
                .map(UserResources::new)
                .collect(Collectors.toList());
    }
}