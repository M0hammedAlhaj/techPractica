package com.spring.techpractica.Application.Admin.Technology.UpdateTechnology;

import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Field.FieldRepository;
import com.spring.techpractica.Core.Shared.Exception.ResourcesDuplicateException;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import com.spring.techpractica.Core.Technology.TechnologyFactory;
import com.spring.techpractica.Core.Technology.TechnologyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UpdateTechnologyUseCase {
    private final TechnologyRepository technologyRepository;
    private final FieldRepository fieldRepository;

    @Transactional
    public Technology execute(UpdateTechnologyCommand command) {
        String name = command.name();

        Technology technology =technologyRepository.getOrThrowByID(command.technologyId());

        if (technologyRepository.existsByName(name)) {
            throw new ResourcesDuplicateException(name);
        }
        List<Field>fields = fieldRepository.findAllByNames(command.fieldNames());

        if (fields.size() != command.fieldNames().size()) {
          throw new ResourcesNotFoundException(command.fieldNames());
        }

        if (!fields.isEmpty()) {
           technology.setFields(fields);
       }
        technology.setName(name);

        return technologyRepository.save(technology);
    }

}
