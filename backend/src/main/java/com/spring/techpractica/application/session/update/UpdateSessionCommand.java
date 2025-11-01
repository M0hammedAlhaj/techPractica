package com.spring.techpractica.application.session.update;

import com.spring.techpractica.core.requirement.model.RequirementRequest;

import java.util.List;
import java.util.UUID;

public record UpdateSessionCommand(UUID userId, UUID sessionId,
                                   String name, String description, boolean isPrivate,
                                   UUID system, List<RequirementRequest> requirements) {
}