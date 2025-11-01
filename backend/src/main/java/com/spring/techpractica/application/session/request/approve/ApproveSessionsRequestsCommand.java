package com.spring.techpractica.application.session.request.approve;

import java.util.UUID;

public record ApproveSessionsRequestsCommand(UUID ownerId,
                                             UUID sessionId,
                                             UUID requestId) {
}
