package com.spring.techpractica.ui.rest.controller.user.auth.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginAccountRequest(@Email String email, @NotBlank String password) {
}
