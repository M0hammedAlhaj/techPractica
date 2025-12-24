package com.spring.techpractica.ui.rest.resources.session;

import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.SessionStatus;
import com.spring.techpractica.ui.rest.resources.requirement.RequirementCollection;
import com.spring.techpractica.ui.rest.resources.session.member.SessionMemberResources;
import com.spring.techpractica.ui.rest.resources.system.SystemResources;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class SessionResources {

    private UUID id;
    private String name;
    private String description;
    private boolean isPrivate;
    private SessionStatus status;
    private SystemResources system;
    private RequirementCollection requirements;
    private UUID ownerId;
    private String ownerFullName;
    private List<SessionMemberResources> members;
    private final String sessionCode;


    public SessionResources(Session session) {
        this.id = session.getId();
        this.name = session.getName();
        this.description = session.getDescription();
        this.isPrivate = session.isPrivate();
        this.status = session.getStatus();
        this.ownerId = session.getOwnerId();
        this.ownerFullName = session.getOwnerFullName();

        if (session.getSystems() != null && !session.getSystems().isEmpty()) {
            this.system = new SystemResources(session.getSystems().getLast());
        }

        if (session.getRequirements() != null && !session.getRequirements().isEmpty()) {
            this.requirements = new RequirementCollection(session.getRequirements());
        }

        if (session.getMembers() != null && !session.getMembers().isEmpty()) {
            this.members = session.getMembers()
                    .stream()
                    .map(SessionMemberResources::new)
                    .toList();
        }

        this.sessionCode = session.getSessionCode();
    }
}