package com.spring.techpractica.Core.SessionMembers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSessionId implements Serializable {
    @Column(name = "session_id")
    private UUID sessionId;

    @Column(name = "user_id")
    private UUID userId;
}

