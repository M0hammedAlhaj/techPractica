package com.spring.techpractica.UI.Rest.Controller.Session.CreateSession.Request;


import java.util.List;

public record CreateSessionRequest(String name,
                                   String description, boolean isPrivate,
                                   String system, List<RequirementRequest> requirements){
}