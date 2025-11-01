package com.spring.techpractica.ui.rest.resources.user;

import com.spring.techpractica.ui.rest.resources.session.SessionCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileResources {
    private final UserResources user;
    private final SessionCollection sessions;
}
