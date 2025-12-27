package com.spring.techpractica.core.system;

import com.spring.techpractica.core.system.entity.System;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class SystemFactory {

    public com.spring.techpractica.core.system.entity.System create(String name) {


        return System.builder()
                .name(name)
                .sessions(new ArrayList<>())
                .build();
    }
}
