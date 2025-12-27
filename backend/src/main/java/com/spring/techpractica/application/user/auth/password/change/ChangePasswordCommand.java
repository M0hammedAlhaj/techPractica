package com.spring.techpractica.application.user.auth.password.change;

import java.util.UUID;

public record ChangePasswordCommand(UUID id, String password) {
}
