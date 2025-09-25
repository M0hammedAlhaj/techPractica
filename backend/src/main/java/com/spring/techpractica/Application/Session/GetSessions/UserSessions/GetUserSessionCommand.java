package com.spring.techpractica.Application.Session.GetSessions.UserSessions;

import java.util.UUID;

public record GetUserSessionCommand(UUID userId, int size, int page) {
}
