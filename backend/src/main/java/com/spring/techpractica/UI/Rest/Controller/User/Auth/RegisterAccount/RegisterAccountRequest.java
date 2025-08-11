package com.spring.techpractica.UI.Rest.Controller.User.Auth.RegisterAccount;

import jakarta.validation.constraints.Email;

public record RegisterAccountRequest(@Email String email, String password, String name) {
}
