package com.spring.techpractica.core.user.event;

import java.util.UUID;

public record ResetPasswordEvent(UUID id, String email, String name) {
}