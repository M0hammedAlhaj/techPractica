package com.spring.techpractica.Core.Requirement;

import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Requirement.Entity.Requirement;
import com.spring.techpractica.Core.Session.Entity.Session;
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
