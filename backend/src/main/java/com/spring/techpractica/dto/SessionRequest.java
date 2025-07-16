package com.spring.techpractica.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class SessionRequest {

    private String sessionName;

    private String categoryName;


}
