package com.spring.techpractica.ui.rest.resources.request.FullRequest;

import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.core.request.model.RequestState;
import com.spring.techpractica.core.technology.entity.Technology;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
public class FullRequestResources {

    private final UUID userId;
    private final String fullName;
    private final Set<Technology> skills;
    private final String brief;
    private final String email;
    private final long totalSessions;
    private final LocalDateTime requestDate;
    private final RequestState state;
    private final UUID requestId;

    public FullRequestResources(Request request, long totalSessions) {
        this.userId = request.getUser().getId();
        this.requestId = request.getId();
        this.brief = request.getBrief();
        this.state = request.getRequestStatus();
        this.fullName = request.getUser().getFullName();
        this.email = request.getUser().getEmail();
        this.skills = request.getUser().getSkills();
        this.totalSessions = totalSessions;
        this.requestDate = request.getAtCreated();
    }
}
