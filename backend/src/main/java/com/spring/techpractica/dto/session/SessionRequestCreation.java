package com.spring.techpractica.dto.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SessionRequestCreation {

    private Long sessionId;

    private String categoryName;

    private String brief;

    private Long reqId;
    
}
