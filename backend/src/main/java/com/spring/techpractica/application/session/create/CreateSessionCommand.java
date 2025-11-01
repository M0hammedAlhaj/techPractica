package com.spring.techpractica.application.session.create;

import com.spring.techpractica.core.requirement.model.RequirementRequest;

import java.util.List;
import java.util.UUID;

public record CreateSessionCommand (UUID userId, String name,
                                    String description, boolean isPrivate,
                                    UUID system, List<RequirementRequest> requirements) {
}