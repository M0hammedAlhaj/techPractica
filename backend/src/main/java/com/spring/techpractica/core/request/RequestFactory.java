package com.spring.techpractica.core.request;

import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.core.request.model.RequestState;
import com.spring.techpractica.core.requirement.entity.Requirement;
import com.spring.techpractica.core.user.User;
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
