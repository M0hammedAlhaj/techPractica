package com.spring.techpractica.ui.rest.controller.user.auth.password.change;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(@NotBlank String password, @NotBlank String confirmPassword) {
}
