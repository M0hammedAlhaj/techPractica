package com.spring.techpractica.UI.Rest.Controller.Session.CreateSession.Request;


import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

public record CreateSessionRequest(String name,
                                   String description, boolean isPrivate,
                                   String system, @UniqueElements List<RequirementRequest> requirements){
}