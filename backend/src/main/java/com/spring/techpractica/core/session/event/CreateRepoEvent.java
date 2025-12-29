package com.spring.techpractica.core.session.event;

import java.util.UUID;

public record CreateRepoEvent(UUID userId,
                              String name,
                              String email,
                              String repoUrl) {
}
