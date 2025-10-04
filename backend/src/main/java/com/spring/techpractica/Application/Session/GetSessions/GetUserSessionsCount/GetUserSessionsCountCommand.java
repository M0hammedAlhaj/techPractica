package com.spring.techpractica.Application.Session.GetSessions.GetUserSessionsCount;

import com.spring.techpractica.Core.Session.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


public record GetUserSessionsCountCommand(UUID userId) {

}
