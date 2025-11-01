package com.spring.techpractica.ui.rest.resources.request;

import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.core.request.model.RequestState;
import lombok.Getter;

import java.util.UUID;

@Getter
public class RequestResources {

    private final UUID userId;
    private final UUID requestId;
    private final String brief;
    private final RequestState state;

    public RequestResources(Request request) {
        this.userId = request.getUser().getId();
        this.requestId = request.getId();
        this.brief = request.getBrief();
        this.state = request.getRequestStatus();
    }
}
