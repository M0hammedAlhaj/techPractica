package com.spring.techpractica.UI.Rest.Controller.User.Auth.ChangePassword;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(@NotBlank String password, @NotBlank String confirmPassword) {
}
