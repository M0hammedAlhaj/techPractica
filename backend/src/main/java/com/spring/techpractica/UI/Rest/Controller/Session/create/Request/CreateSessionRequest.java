package com.spring.techpractica.UI.Rest.Controller.Session.create.Request;


import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;
import java.util.UUID;

public record CreateSessionRequest(String name,
                                   String description, boolean isPrivate,
                                   UUID system, @UniqueElements @Valid List<RequirementRequest> requirements){
}