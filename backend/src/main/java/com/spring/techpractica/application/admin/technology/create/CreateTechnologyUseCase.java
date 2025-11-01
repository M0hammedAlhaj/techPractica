package com.spring.techpractica.application.admin.technology.create;

import com.spring.techpractica.core.field.entity.Field;
import com.spring.techpractica.core.field.FieldRepository;
import com.spring.techpractica.core.shared.Exception.ResourcesDuplicateException;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.technology.entity.Technology;
import com.spring.techpractica.core.technology.TechnologyFactory;
import com.spring.techpractica.core.technology.TechnologyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CreateTechnologyUseCase {
    private final TechnologyRepository technologyRepository;
    private final TechnologyFactory technologyFactory;
    private final FieldRepository fieldRepository;

    @Transactional
    public Technology execute(CreateTechnologyCommand command) {
        String name = command.name();
        if (technologyRepository.existsByName(name)) {
            throw new ResourcesDuplicateException(name);
        }
        List<Field>fields = fieldRepository.findAllByNames(command.fieldNames());

        if (fields.size() != command.fieldNames().size()) {
          throw new ResourcesNotFoundException(command.fieldNames());
        }

        Technology technology = technologyFactory.create(name);

        if (!fields.isEmpty()) {
           technology.setFields(fields);
       }

        return technologyRepository.save(technology);
    }

}
