package com.spring.techpractica.validator;

import com.spring.techpractica.dto.SessionAuthorization;
import com.spring.techpractica.exception.AuthenticationException;
import com.spring.techpractica.model.SessionRole;
import org.springframework.stereotype.Component;

@Component
public class SessionAuthorizationValidator implements Validator<SessionAuthorization> {


    @Override
    public void validate(SessionAuthorization obj) {
        if (obj.getSessionRole().equals(obj.getAuthenticatedUserSession().getScopedRole())) {
            throw new AuthenticationException("User must be an OWNER to perform this action.");
        }
    }
}
