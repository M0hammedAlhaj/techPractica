package com.spring.techpractica.UI.Rest.Resources.Technology;

import com.fasterxml.jackson.annotation.JsonValue;
import com.spring.techpractica.UI.Rest.Resources.Field.FieldCollection;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechnologyResources {
    private UUID id;
    private String name;
    private FieldCollection fields;
}