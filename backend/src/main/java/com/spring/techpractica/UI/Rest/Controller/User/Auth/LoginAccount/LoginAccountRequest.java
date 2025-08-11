package com.spring.techpractica.UI.Rest.Controller.User.Auth.LoginAccount;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginAccountRequest(@Email String email, @NotBlank String password) {
}
