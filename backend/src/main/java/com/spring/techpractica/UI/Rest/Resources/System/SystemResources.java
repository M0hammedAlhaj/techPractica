package com.spring.techpractica.UI.Rest.Resources.System;

import lombok.*;

import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SystemResources {

    private UUID id;
    private String name;
}
