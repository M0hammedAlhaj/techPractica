package com.spring.techpractica.ui.rest.controller.session.create.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class RequirementRequest {

    private UUID field;

    @UniqueElements
    private List<UUID> technologies;
}
