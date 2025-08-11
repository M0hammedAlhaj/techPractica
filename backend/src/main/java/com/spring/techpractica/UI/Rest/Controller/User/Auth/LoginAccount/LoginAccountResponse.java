package com.spring.techpractica.UI.Rest.Controller.User.Auth.LoginAccount;

import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.UI.Rest.Resources.User.UserResources;
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
