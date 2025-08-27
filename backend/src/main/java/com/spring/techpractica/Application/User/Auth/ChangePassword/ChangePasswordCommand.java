package com.spring.techpractica.Application.User.Auth.ChangePassword;

import java.util.UUID;

public record ChangePasswordCommand(UUID id, String password) {
}
