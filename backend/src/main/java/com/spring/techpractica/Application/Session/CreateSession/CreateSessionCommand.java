package com.spring.techpractica.Application.Session.CreateSession;

import com.spring.techpractica.Core.Requirement.Model.RequirementRequest;

import java.util.List;
import java.util.UUID;

public record CreateSessionCommand (UUID userId, String name,
                                    String description, boolean isPrivate,
                                    String system, List<RequirementRequest> requirements) {
}