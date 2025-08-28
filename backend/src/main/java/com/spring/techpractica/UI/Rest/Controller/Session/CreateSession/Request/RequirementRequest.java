package com.spring.techpractica.UI.Rest.Controller.Session.CreateSession.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class RequirementRequest {

    private String fieldName;
    private Set<String> technologies;
}
