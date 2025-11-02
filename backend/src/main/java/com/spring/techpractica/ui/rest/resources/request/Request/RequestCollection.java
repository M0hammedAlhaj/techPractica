package com.spring.techpractica.ui.rest.resources.request.Request;

import com.fasterxml.jackson.annotation.JsonValue;
import com.spring.techpractica.core.request.entity.Request;
import lombok.Getter;

import java.util.List;

@Getter
public class RequestCollection {
    @JsonValue
    private final List<RequestResources> requests;

    public RequestCollection(List<Request> requests) {
        this.requests = requests.stream().map(RequestResources::new).toList();

    }
}
