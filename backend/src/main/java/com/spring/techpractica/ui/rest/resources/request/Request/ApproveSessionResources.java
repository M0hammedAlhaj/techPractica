package com.spring.techpractica.ui.rest.resources.request.Request;

import com.spring.techpractica.core.notification.entity.Notification;
import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.ui.rest.resources.notification.NotificationResources;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ApproveSessionResources {

    private RequestResources request;
    private NotificationResources  notification;

    public ApproveSessionResources(Request request, Notification notification) {
        this.request = new RequestResources(request);
        this.notification = new NotificationResources(notification);
    }
}