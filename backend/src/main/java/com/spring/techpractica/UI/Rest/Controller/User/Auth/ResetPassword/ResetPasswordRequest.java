package com.spring.techpractica.UI.Rest.Controller.User.Auth.ResetPassword;

import jakarta.validation.constraints.Email;

public record ResetPasswordRequest(@Email String email) {
}
