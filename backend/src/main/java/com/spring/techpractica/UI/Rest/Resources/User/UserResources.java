package com.spring.techpractica.UI.Rest.Resources.User;

import com.spring.techpractica.Core.User.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResources {
    private final String id;
    private final String name;
    private final String email;

    public UserResources(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.id = user.getId().toString();
    }
}