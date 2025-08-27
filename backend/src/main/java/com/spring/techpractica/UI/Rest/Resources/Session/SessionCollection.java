package com.spring.techpractica.UI.Rest.Resources.Session;

import com.fasterxml.jackson.annotation.JsonValue;
import com.spring.techpractica.Core.Session.Entity.Session;
import lombok.Getter;

import java.util.List;

@Getter
public class SessionCollection {

    @JsonValue
    private final List<SessionResources> sessions;

    public SessionCollection(List<Session> sessions) {
        this.sessions = sessions.stream().map(SessionResources::new)
                .toList();
    }
}