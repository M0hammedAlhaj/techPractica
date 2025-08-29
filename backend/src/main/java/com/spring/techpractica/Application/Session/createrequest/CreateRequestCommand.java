package com.spring.techpractica.Application.Session.createrequest;

import com.spring.techpractica.Core.Request.model.RequestState;

import java.util.UUID;

public record CreateRequestCommand(UUID sessionId,
                                   UUID userId,
                                   UUID requirementId,
                                   String brief,
                                   RequestState state) {
}
