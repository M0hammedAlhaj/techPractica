package com.spring.techpractica.ui.rest.controller.user.auth.login;

import com.spring.techpractica.core.user.User;
import com.spring.techpractica.ui.rest.resources.user.UserResources;
import lombok.Getter;

@Getter
public class LoginAccountResponse {
    private final UserResources user;

    private final String token;

    public LoginAccountResponse(User user, String token) {
        this.user = new UserResources(user);
        this.token = token;
    }
}
