package com.spring.techpractica.core.session.entity;

import com.spring.techpractica.core.requirement.entity.Requirement;
import com.spring.techpractica.core.session.SessionStatus;
import com.spring.techpractica.core.session.members.Entity.SessionMember;
import com.spring.techpractica.core.session.members.model.Role;
import com.spring.techpractica.core.shared.BaseEntity;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.system.entity.System;
import com.spring.techpractica.core.task.entity.Task;
import com.spring.techpractica.core.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "SESSIONS")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Session extends BaseEntity {
    @Column(name = "session_name")
    private String name;

    @Column(name = "session_description",
            length = 1000)
    private String description;

    @Column(name = "is_private")
    private boolean isPrivate;

    @Column(name = "session_code", unique = true)
    private String sessionCode;

    @OneToMany(mappedBy = "session",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE , CascadeType.MERGE},
            orphanRemoval = true)
    private List<SessionMember> members = new ArrayList<>();

    @Column(name = "status")
    private SessionStatus status;

    @OneToMany(mappedBy = "session",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.REMOVE,
                    CascadeType.PERSIST},
            orphanRemoval = true)
    private List<Requirement> requirements = new ArrayList<>();

    @OneToMany(mappedBy = "session",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private List<Task> tasks = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "SYSTEMS_SESSIONS",
            joinColumns = @JoinColumn(name = "session_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "system_id", referencedColumnName = "id")
    )
    private List<System> systems = new ArrayList<>();

    public void addMember(SessionMember sessionMember) {
        if (members == null) {
            members = new ArrayList<>();
        }
        sessionMember.setSession(this);
        members.add(sessionMember);
    }

    public void addSystem(System system) {
        if (systems == null) {
            systems = new ArrayList<>();
        }
        systems.add(system);
    }

    public void addRequirement(Requirement requirement) {
        if (requirements == null) {
            requirements = new ArrayList<>();
        }
        requirements.add(requirement);
        requirement.setSession(this);
    }

    public void addBasicInfo(String name, String description, boolean isPrivate) {
        this.name = name;
        this.description = description;
        this.isPrivate = isPrivate;
    }

    public boolean isOwner(UUID userId){
        return members.stream()
                .anyMatch(member -> member.getUser().getId().equals(userId)
                        && member.getRole() == Role.OWNER);
    }

    public void clearRequirements() {
        requirements.clear();
    }

    public User getOwner(){
        return members.stream()
                .map(SessionMember::getUser)
                .filter(user -> isOwner(user.getId()))
                .findFirst()
                .orElse(null);
    }

    public String getOwnerFullName() {
        User owner = getOwner();
        return owner != null ? owner.getFullName() : null;
    }

    public UUID getOwnerId() {
        User owner = getOwner();
        return owner != null ? owner.getId() : null;
    }

    public void runSession(){
        if (status.equals(SessionStatus.RUNNING)) {
            throw new UnsupportedOperationException("The session is already running.");
        }
        this.status = SessionStatus.RUNNING;
    }

    public boolean isMember(UUID userId) {
        return this.members.stream()
                .anyMatch(member -> member.getUser().getId().equals(userId));
    }

    public void generateSessionCode(String code) {
        this.sessionCode = code;
    }

    public void removeMember(UUID participantId) {
        boolean removed = members.removeIf(member ->
                member.getUser().getId().equals(participantId)
        );

        if (!removed) {
            throw new ResourcesNotFoundException(participantId);
        }
    }
}
