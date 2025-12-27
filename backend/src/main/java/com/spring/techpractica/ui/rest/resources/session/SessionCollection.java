package com.spring.techpractica.ui.rest.resources.session;

import com.spring.techpractica.core.session.entity.Session;
import lombok.Getter;

import java.util.List;

@Getter
public class SessionCollection {

    private final List<SessionResources> sessions;

    private final long totalItems;

    private final int totalPages;

    private final int pageSize=6;

    public SessionCollection(List<Session> sessions,long totalItems) {
        this.sessions = sessions.stream().map(SessionResources::new)
                .toList();
        this.totalItems = totalItems;

        this.totalPages = (int) Math.ceil((double) totalItems /pageSize);
    }

}