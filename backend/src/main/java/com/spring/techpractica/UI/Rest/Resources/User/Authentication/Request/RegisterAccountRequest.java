package com.spring.techpractica.UI.Rest.Resources.User.Authentication.Request;

import jakarta.validation.constraints.Email;

public record RegisterAccountRequest(@Email String email, String password, String name) {
}
