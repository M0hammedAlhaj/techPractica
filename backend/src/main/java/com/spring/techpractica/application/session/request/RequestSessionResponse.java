package com.spring.techpractica.application.session.request;

import com.spring.techpractica.core.notification.entity.Notification;
import com.spring.techpractica.core.request.entity.Request;
import lombok.Getter;

@Getter
public class RequestSessionResponse {

    private final Notification notification;
    private final Request request;

    public RequestSessionResponse(Notification notification, Request request) {
        this.notification = notification;
        this.request = request;
    }
}