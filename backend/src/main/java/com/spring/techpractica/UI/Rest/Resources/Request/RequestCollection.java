package com.spring.techpractica.UI.Rest.Resources.Request;

import com.fasterxml.jackson.annotation.JsonValue;
import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Request.Entity.Request;
import com.spring.techpractica.UI.Rest.Resources.Field.FieldResources;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

@Getter
public class RequestCollection {
    @JsonValue
    private final List<RequestResources> requests;

    public RequestCollection(List<Request> requests) {
        this.requests = requests.stream().map(RequestResources::new).toList();

    }
}
