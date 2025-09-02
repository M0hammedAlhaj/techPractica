package com.spring.techpractica.Core.System;

import com.spring.techpractica.Core.System.Entity.System;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class SystemFactory {

    public com.spring.techpractica.Core.System.Entity.System create(String name) {


        return System.builder()
                .name(name)
                .sessions(new ArrayList<>())
                .build();
    }
}
