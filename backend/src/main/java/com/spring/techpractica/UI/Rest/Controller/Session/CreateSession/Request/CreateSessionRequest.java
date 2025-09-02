package com.spring.techpractica.UI.Rest.Controller.Session.CreateSession.Request;


import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;
import java.util.UUID;

public record CreateSessionRequest(String name,
                                   String description, boolean isPrivate,
                                   UUID system, @UniqueElements List<RequirementRequest> requirements){
}