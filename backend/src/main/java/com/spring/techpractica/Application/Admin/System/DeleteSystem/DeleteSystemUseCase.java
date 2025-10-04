package com.spring.techpractica.Application.Admin.System.DeleteSystem;

import com.spring.techpractica.Core.Field.Entity.Field;
import com.spring.techpractica.Core.Field.FieldRepository;
import com.spring.techpractica.Core.System.Entity.System;
import com.spring.techpractica.Core.System.SystemRepository;
import com.spring.techpractica.infrastructure.Jpa.System.JpaSystem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DeleteSystemUseCase {
    private final SystemRepository systemRepository;
    private final JpaSystem jpaSystem;


    @Transactional
    public void execute(DeleteSystemCommand command) {

        System system = systemRepository.getOrThrowByID(command.systemId());

         systemRepository.deleteById(system.getId());
    }
}
