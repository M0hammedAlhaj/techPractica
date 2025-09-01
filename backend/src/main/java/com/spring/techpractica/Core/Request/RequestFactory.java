package com.spring.techpractica.Core.Request;

import com.spring.techpractica.Core.Request.Entity.Request;
import com.spring.techpractica.Core.Request.model.RequestState;
import com.spring.techpractica.Core.Requirement.Entity.Requirement;
import com.spring.techpractica.Core.User.User;
import org.springframework.stereotype.Component;

@Component
public class RequestFactory {

    public Request createRequest(User user, Requirement requirement, String brief) {
        return Request.builder()
                .user(user)
                .requirement(requirement)
                .brief(brief)
                .requestStatus(RequestState.PENDING)
                .build();
    }
}
