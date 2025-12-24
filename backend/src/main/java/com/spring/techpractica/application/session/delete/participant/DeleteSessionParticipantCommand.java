package com.spring.techpractica.application.session.delete.participant;

import java.util.UUID;

public record DeleteSessionParticipantCommand(UUID ownerId, UUID participantId, UUID sessionId) {
}
