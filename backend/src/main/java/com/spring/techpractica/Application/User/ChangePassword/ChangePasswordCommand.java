package com.spring.techpractica.Application.User.ChangePassword;

import java.util.UUID;

public record ChangePasswordCommand(UUID id, String password) {
}
