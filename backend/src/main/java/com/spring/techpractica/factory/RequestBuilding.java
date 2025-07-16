package com.spring.techpractica.factory;

import com.spring.techpractica.model.entity.Request;
import com.spring.techpractica.model.entity.Session;
import com.spring.techpractica.model.entity.User;

public class RequestBuilding {

    public static Request createRequestFrom(Session session,
                                            User user,
                                            String brief) {
        return Request.builder()
                .session(session)
                .user(user)
                .brief(brief)
                .build();
    }
}
