package com.spring.techpractica.ui.rest.resources.user;

import com.spring.techpractica.core.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MinimalUserResources {

    private final String id;
    private final String fullName;

    public MinimalUserResources(User user) {
        this.id = user.getId().toString();
        this.fullName = user.getFirstName() + " " + user.getLastName();
    }
}