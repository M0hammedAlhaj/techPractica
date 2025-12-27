package com.spring.techpractica.ui.rest.resources.request.FullRequest;

import com.fasterxml.jackson.annotation.JsonValue;
import com.spring.techpractica.application.session.get.user.sessions.count.GetUserSessionsCountCommand;
import com.spring.techpractica.application.session.get.user.sessions.count.GetUserSessionsCountUseCase;
import com.spring.techpractica.application.session.request.get.sessions.requests.GetSessionsRequestsUseCase;
import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.ui.rest.resources.request.Request.RequestResources;
import lombok.Getter;

import java.util.List;

@Getter
public class FullRequestCollection {
    @JsonValue
    private final List<FullRequestResources> requests;

    public FullRequestCollection(List<Request> requests, GetUserSessionsCountUseCase getUserSessionsCountUseCase) {

        this.requests = requests.stream()
                .map(req -> new FullRequestResources(
                        req,
                        getUserSessionsCountUseCase.execute(
                                new GetUserSessionsCountCommand(req.getUser().getId())
                        )
                ))
                .toList();
    }
}
