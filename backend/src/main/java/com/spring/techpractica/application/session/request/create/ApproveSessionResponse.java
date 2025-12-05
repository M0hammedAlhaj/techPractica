package com.spring.techpractica.application.session.request.create;

import com.spring.techpractica.core.notification.entity.Notification;
import com.spring.techpractica.core.request.entity.Request;
import lombok.Getter;

@Getter
public class ApproveSessionResponse {

    private final Notification notification;
    private final Request request;

    public ApproveSessionResponse(Notification notification, Request request) {
        this.notification = notification;
        this.request = request;
    }
}