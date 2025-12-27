package com.spring.techpractica.application.session.get.user.sessions;

import java.util.UUID;

public record GetUserSessionCommand(UUID userId, int size, int page) {
}
