package com.spring.techpractica.dto;

import com.spring.techpractica.model.SessionRole;
import com.spring.techpractica.model.entity.AuthenticatedUserSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionAuthorization {

    private SessionRole sessionRole;

    private AuthenticatedUserSession authenticatedUserSession;
}
