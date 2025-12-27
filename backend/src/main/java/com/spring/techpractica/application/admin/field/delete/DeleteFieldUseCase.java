package com.spring.techpractica.application.admin.field.delete;

import com.spring.techpractica.application.admin.technology.delete.DeleteTechnologyCommand;
import com.spring.techpractica.application.admin.technology.delete.DeleteTechnologyUseCase;
import com.spring.techpractica.core.field.entity.Field;
import com.spring.techpractica.core.field.FieldRepository;
import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.shared.Exception.ResourcesDuplicateException;
import com.spring.techpractica.core.technology.TechnologyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DeleteFieldUseCase {
    private final FieldRepository fieldRepository;

    private final TechnologyRepository technologyRepository;

    private final SessionRepository sessionRepository;

    private final DeleteTechnologyUseCase deleteTechnologyUseCase;

    @Transactional
    public void execute(DeleteFieldCommand command) {

        Field field = fieldRepository.getOrThrowByID(command.fieldId());

        if (!sessionRepository.getSessionsByFieldId(command.fieldId()).isEmpty()) {
            throw new ResourcesDuplicateException("there is a session with this field id " + command.fieldId());
        }
        technologyRepository.findAllByFieldId(field.getId()).forEach(
                technology ->
                {deleteTechnologyUseCase.execute
                        (new DeleteTechnologyCommand(technology.getId()));}
        );
         fieldRepository.deleteById(field.getId());
    }
}
