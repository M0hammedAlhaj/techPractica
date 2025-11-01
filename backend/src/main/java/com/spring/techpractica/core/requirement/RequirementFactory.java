package com.spring.techpractica.core.requirement;

import com.spring.techpractica.core.field.entity.Field;
import com.spring.techpractica.core.requirement.entity.Requirement;
import com.spring.techpractica.core.session.entity.Session;
import org.springframework.stereotype.Component;

@Component
public class RequirementFactory {

    public Requirement create(Session session, Field field) {
        return Requirement.builder()
                .session(session)
                .field(field)
                .build();
    }
}
