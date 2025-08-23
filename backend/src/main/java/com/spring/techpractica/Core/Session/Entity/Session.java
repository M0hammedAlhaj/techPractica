package com.spring.techpractica.Core.Session.Entity;

import com.spring.techpractica.Core.SessionMembers.Entity.SessionMembers;
import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Request.Entity.Request;
import com.spring.techpractica.Core.Requirement.Entity.Requirement;
import com.spring.techpractica.Core.Shared.BaseEntity;
import com.spring.techpractica.Core.System.Entity.System;
import com.spring.techpractica.Core.Task.Entity.Task;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "SESSIONS")
@NoArgsConstructor
public class Session extends BaseEntity {
    @Column(name = "session_name")
    private String sessionName;

    @Column(name = "session_description",
            length = 1000)
    private String sessionDescription;

    @Column(name = "is_private")
    private boolean isPrivate;

    @OneToMany(mappedBy = "session",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<SessionMembers> members = new ArrayList<>();

    @Column(name = "is_running")
    private boolean sessionIsRunning;


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

    @OneToMany(mappedBy = "session",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,
                    CascadeType.REMOVE, CascadeType.MERGE})
    private List<Request> requests = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "SYSTEMS_SESSIONS",
            joinColumns = @JoinColumn(name = "session_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "system_id", referencedColumnName = "id")
    )
    private List<System> systems = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "TECHNOLOGIES_SESSIONS"
            , joinColumns = @JoinColumn(name = "technology_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "session_id", referencedColumnName = "id")
    )
    private List<Technology> technologies = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "CATEGORIES_SESSIONS",
            joinColumns = @JoinColumn(name = "field_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "session_id", referencedColumnName = "id")
    )
    private List<Field> fields = new ArrayList<>();
}
