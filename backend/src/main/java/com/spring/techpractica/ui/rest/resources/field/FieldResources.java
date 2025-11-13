package com.spring.techpractica.ui.rest.resources.field;

import com.spring.techpractica.core.field.entity.Field;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldResources {
    private UUID id;
    private String name;

}
