package com.spring.techpractica.application.session.request.reject;

import java.util.UUID;

public record RejectSessionsRequestsCommand(UUID ownerId,
                                            UUID sessionId,
                                            UUID requestId) {
}
