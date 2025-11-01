package com.spring.techpractica.application.session.request.create;

import java.util.UUID;

public record CreateRequestCommand(UUID userId,
                                   UUID requirementId,
                                   String brief) {
}
