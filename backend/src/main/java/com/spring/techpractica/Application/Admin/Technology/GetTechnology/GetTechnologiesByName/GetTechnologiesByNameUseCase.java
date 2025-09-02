package com.spring.techpractica.Application.Admin.Technology.GetTechnology.GetTechnologiesByName;

import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import com.spring.techpractica.Core.Technology.TechnologyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetTechnologiesByNameUseCase {
    private final TechnologyRepository technologyRepository;

    public List<Technology> execute(GetTechnologiesByNameCommand command) {
        List<Technology> technologies = technologyRepository.findAllByNames(command.names());
        if (technologies.size() != command.names().size()) {
            throw new ResourcesNotFoundException(command.names());
        }
        return technologies;
    }
}
