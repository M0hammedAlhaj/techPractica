package com.spring.techpractica.core.technology;
import com.spring.techpractica.core.technology.entity.Technology;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class TechnologyFactory {

    public Technology create(String name) {


        return Technology.builder()
                .name(name)
                .fields(new ArrayList<>())
                .build();
    }
}
