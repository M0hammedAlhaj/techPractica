package com.spring.techpractica.Application.Session.update;

import com.spring.techpractica.Core.Requirement.Model.RequirementRequest;

import java.util.List;
import java.util.UUID;

public record UpdateSessionCommand(UUID userId, UUID sessionId,
                                   String name, String description, boolean isPrivate,
                                   UUID system, List<RequirementRequest> requirements) {
}