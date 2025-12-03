package com.spring.techpractica.ui.rest.resources.session;

import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.members.model.Role;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class UserSessionCollection {

    private final List<UserSessionResources> sessions;

    private final long totalItems;

    private final int totalPages;

    private final int pageSize = 6;


    public UserSessionCollection(List<Session> sessions, long totalItems, UUID userId) {
        this.sessions = (sessions == null ? java.util.List.<Session>of() : sessions)
                .stream()
                .map(s -> new UserSessionResources(
                        s,
                        (s.getOwner().getId().equals(userId))
                                ? Role.OWNER
                                : Role.PARTICIPATE
                        , userId
                ))
                .collect(Collectors.toList());

        this.totalItems = totalItems;
        this.totalPages = (int) Math.ceil((double) totalItems / pageSize);

    }


}