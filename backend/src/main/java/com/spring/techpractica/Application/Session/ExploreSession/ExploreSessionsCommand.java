package com.spring.techpractica.Application.Session.ExploreSession;

import java.util.Optional;
import java.util.UUID;

public record ExploreSessionsCommand(Optional<UUID> userId, int page, int size) {
}
