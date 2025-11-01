package com.spring.techpractica.application.admin.technology.delete;

import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.shared.Exception.ResourcesDuplicateException;
import com.spring.techpractica.core.technology.entity.Technology;
import com.spring.techpractica.core.technology.TechnologyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DeleteTechnologyUseCase {
    private final TechnologyRepository technologyRepository;

    private final SessionRepository sessionRepository;
    @Transactional
    public void execute(DeleteTechnologyCommand command) {

        Technology technology = technologyRepository.getOrThrowByID(command.technologyId());

        if (!sessionRepository.getSessionsByTechnologyId(command.technologyId()).isEmpty()) {
            throw new ResourcesDuplicateException("session with technology id " + command.technologyId() + " exists");
        }
         technologyRepository.deleteById(technology.getId());
    }
}
