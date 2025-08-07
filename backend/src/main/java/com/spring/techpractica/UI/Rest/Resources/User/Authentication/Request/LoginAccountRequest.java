package com.spring.techpractica.UI.Rest.Resources.User.Authentication.Request;

import jakarta.validation.constraints.Email;

public record LoginAccountRequest(@Email String email, String password) {
}
