package com.spring.techpractica.Application.Session.GetSessionsRequests;

import java.util.UUID;

public record GetSessionsRequestsCommand(UUID userId,
                                         UUID sessionId) {
}
