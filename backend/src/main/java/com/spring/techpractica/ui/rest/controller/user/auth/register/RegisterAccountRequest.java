package com.spring.techpractica.ui.rest.controller.user.auth.register;

import jakarta.validation.constraints.Email;

public record RegisterAccountRequest(@Email String email, String password, String name) {
}
