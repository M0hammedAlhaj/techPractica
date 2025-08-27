package com.spring.techpractica.Application.Admin.Technology.GetTechnology.GetAllTechnologies;

import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import com.spring.techpractica.Core.Technology.TechnologyRepository;
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
