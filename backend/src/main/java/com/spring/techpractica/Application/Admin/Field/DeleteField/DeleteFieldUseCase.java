package com.spring.techpractica.Application.Admin.Field.DeleteField;

import com.spring.techpractica.Application.Admin.Technology.DeleteTechnology.DeleteTechnologyCommand;
import com.spring.techpractica.Application.Admin.Technology.DeleteTechnology.DeleteTechnologyUseCase;
import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Field.FieldFactory;
import com.spring.techpractica.Core.Field.FieldRepository;
import com.spring.techpractica.Core.Shared.Exception.ResourcesDuplicateException;
import com.spring.techpractica.Core.Technology.TechnologyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DeleteFieldUseCase {
    private final FieldRepository fieldRepository;

    private final TechnologyRepository technologyRepository;

    private final DeleteTechnologyUseCase deleteTechnologyUseCase;
    @Transactional
    public void execute(DeleteFieldCommand command) {

        Field field = fieldRepository.getOrThrowByID(command.fieldId());
        technologyRepository.findAllByFieldId(field.getId()).forEach(
                technology ->
                {deleteTechnologyUseCase.execute
                        (new DeleteTechnologyCommand(technology.getId()));}
        );
         fieldRepository.deleteById(field.getId());
    }
}
