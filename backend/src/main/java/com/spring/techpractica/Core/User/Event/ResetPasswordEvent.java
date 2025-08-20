package com.spring.techpractica.Core.User.Event;

import java.util.UUID;

public record ResetPasswordEvent(UUID id, String email, String name) {
}
