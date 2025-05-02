package com.spring.techpractica.factory;

import com.spring.techpractica.model.entity.techSkills.Technology;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

@Component
public class TechnologyFactory {

    public TechnologyFactory() {}

    public static Technology createTechnology(String technologyName) {
        return Technology.builder().
                technologyName(technologyName).
                categories(null).
                build();
    }
}
