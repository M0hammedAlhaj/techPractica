package com.spring.techpractica.dto.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionRequest {

    private String nameSession;

    private String descriptionSession;

    private boolean privateSession;

    private String system;

    private List<String> technologies;

    private List<String> categories;

}
