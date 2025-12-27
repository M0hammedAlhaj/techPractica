package com.spring.techpractica.application.admin.technology.get.all;

import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.technology.entity.Technology;
import com.spring.techpractica.core.technology.TechnologyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllTechnologiesUseCase {
    private final TechnologyRepository technologyRepository;

    public List<Technology> execute(GetAllTechnologiesCommand command) {
        List<Technology> technologies = technologyRepository.findAll();

        if (technologies.isEmpty()) {
            throw new ResourcesNotFoundException("No technologies found");
        }
        return technologies;
    }
}
