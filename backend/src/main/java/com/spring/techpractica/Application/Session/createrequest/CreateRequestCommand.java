package com.spring.techpractica.Application.Session.createrequest;

import java.util.UUID;

public record CreateRequestCommand(UUID userId,
                                   UUID requirementId,
                                   String brief) {
}
