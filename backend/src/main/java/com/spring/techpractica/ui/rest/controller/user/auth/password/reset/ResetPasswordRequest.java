package com.spring.techpractica.ui.rest.controller.user.auth.password.reset;

import jakarta.validation.constraints.Email;

public record ResetPasswordRequest(@Email String email) {
}
