package com.spring.techpractica.dto.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class SessionResponse {

    private long id;

    private String sessionName;

    private String sessionDescription;

    private List<String> technologies;

    private String system;

    private List<String> categories;

    private boolean privateSession;

    private String ownerName;

}
