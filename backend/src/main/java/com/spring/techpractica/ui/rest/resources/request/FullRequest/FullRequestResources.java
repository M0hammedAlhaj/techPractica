package com.spring.techpractica.ui.rest.resources.request.FullRequest;

import com.spring.techpractica.core.field.entity.Field;
import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.core.request.model.RequestState;
import com.spring.techpractica.core.technology.entity.Technology;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.ui.rest.resources.field.FieldResources;
import com.spring.techpractica.ui.rest.resources.technology.TechnologyCollection;
import com.spring.techpractica.ui.rest.resources.technology.TechnologyResources;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
public class FullRequestResources {

    private final UUID userId;
    private final String fullName;
    private final TechnologyCollection skills;
    private final String brief;
    private final String email;
    private final long totalSessions;
    private final LocalDateTime requestDate;
    private final RequestState state;
    private final UUID requestId;
    private final FieldResources field;
    public FullRequestResources(Request request, long totalSessions) {
        User user = request.getUser();
        this.userId = user.getId();
        this.requestId = request.getId();
        this.brief = request.getBrief();
        this.state = request.getRequestStatus();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.skills = new TechnologyCollection(user.getSkills().stream().toList());
        this.totalSessions = totalSessions;
        this.requestDate = request.getAtCreated();
        this.field=new FieldResources(request.getRequirement().getField().getId()
                ,request.getRequirement().getField().getName());

    }
}
