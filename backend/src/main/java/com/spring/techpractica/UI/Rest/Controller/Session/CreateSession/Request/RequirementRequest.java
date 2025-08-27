package com.spring.techpractica.UI.Rest.Controller.Session.CreateSession.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class RequirementRequest {

    private String fieldName;
    private List<String> technologies;
}
