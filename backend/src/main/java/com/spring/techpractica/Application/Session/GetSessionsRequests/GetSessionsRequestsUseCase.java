package com.spring.techpractica.Application.Session.GetSessionsRequests;

import com.spring.techpractica.Core.Request.Entity.Request;
import com.spring.techpractica.Core.Request.RequestFactory;
import com.spring.techpractica.Core.Request.RequestRepository;
import com.spring.techpractica.Core.Requirement.Entity.Requirement;
import com.spring.techpractica.Core.Requirement.RequirementRepository;
import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.Session.SessionRepository;
import com.spring.techpractica.Core.Shared.Exception.OperationDuplicateException;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.User.Exception.IncompleteAccountException;
import com.spring.techpractica.Core.User.Exception.UserAuthenticationException;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GetSessionsRequestsUseCase {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;


    public List<Request> execute(GetSessionsRequestsCommand command) {

        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new ResourcesNotFoundException(command.userId()));

        Session session = sessionRepository.findById(command.sessionId())
                .orElseThrow(() -> new ResourcesNotFoundException(command.sessionId()));

        if(!session.isOwner(user.getId())){
            throw new UserAuthenticationException("You are not owner of this session");
        }

        List<Request> requests = sessionRepository.getRequestsBySession(command.sessionId());

        if(requests.isEmpty()){
            requests = new ArrayList<>();
        }

        return requests;
    }
}
