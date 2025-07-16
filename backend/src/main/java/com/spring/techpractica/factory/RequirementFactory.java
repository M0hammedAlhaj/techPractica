package com.spring.techpractica.factory;

import com.spring.techpractica.model.entity.Requirement;
import com.spring.techpractica.model.entity.Session;
import com.spring.techpractica.model.entity.techSkills.Category;
import org.springframework.stereotype.Component;

@Component
public class RequirementFactory {

    private RequirementFactory() {

    }

    public static Requirement createRequirement(Session session, Category category) {

        return Requirement.builder()
                .session(session)
                .category(category)
                .build();
    }
}
