package com.spring.techpractica.ui.rest.resources.session;

import com.spring.techpractica.core.session.SessionStatus;
import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.members.model.Role;
import com.spring.techpractica.ui.rest.resources.requirement.RequirementCollection;
import com.spring.techpractica.ui.rest.resources.system.SystemResources;
import com.spring.techpractica.ui.rest.resources.user.MinimalUserResources;
import com.spring.techpractica.ui.rest.resources.user.UserResources;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class UserSessionResources {

    private UUID id;
    private String name;
    private String description;
    private boolean isPrivate;
    private SessionStatus status;
    private SystemResources system;
    private RequirementCollection requirements;
    private UUID ownerId;
    private String ownerFullName;
    private Role role;
    private List<MinimalUserResources> users;
    private final String sessionCode;


    public UserSessionResources(Session session, Role role) {
        this.id = session.getId();
        this.name = session.getName();
        this.description = session.getDescription();
        this.isPrivate = session.isPrivate();
        this.status = session.getStatus();
        this.ownerId = session.getOwnerId();
        this.ownerFullName = session.getOwnerFullName();
        this.role = role;
        this.sessionCode = session.getSessionCode();

        if (session.getSystems() != null && !session.getSystems().isEmpty()) {
            this.system = new SystemResources(session.getSystems().getLast());
        }

        if (session.getRequirements() != null && !session.getRequirements().isEmpty()) {
            this.requirements = new RequirementCollection(session.getRequirements());
        }

        this.users = session.getMembers()
                .stream()
                .map(member -> new MinimalUserResources(member.getUser()))
                .collect(Collectors.toList());
    }
}