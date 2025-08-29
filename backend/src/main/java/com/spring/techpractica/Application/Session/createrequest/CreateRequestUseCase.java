package com.spring.techpractica.Application.Session.createrequest;

import com.spring.techpractica.Core.Request.Entity.Request;
import com.spring.techpractica.Core.Request.RequestFactory;
import com.spring.techpractica.Core.Request.RequestRepository;
import com.spring.techpractica.Core.Requirement.Entity.Requirement;
import com.spring.techpractica.Core.Requirement.RequirementRepository;
import com.spring.techpractica.Core.Shared.Exception.OperationDuplicateException;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.User.Exception.IncompleteAccountException;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CreateRequestUseCase {

    private final UserRepository userRepository;
    private final RequirementRepository requirementRepository;
    private final RequestFactory requestFactory;
    private final RequestRepository requestRepository;

    @Transactional
    public Request execute(CreateRequestCommand command) {
        UUID userId = command.userId();
        UUID requirementId = command.requirementId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourcesNotFoundException(userId));

        Requirement requirement = requirementRepository.findById(requirementId)
                .orElseThrow(() -> new ResourcesNotFoundException(requirementId));

        if (!user.isProfileComplete()) {
            throw new IncompleteAccountException("Please complete your profile");
        }

        if (requestRepository.existsByUserIdAndRequirementId(userId, requirementId)) {
            throw new OperationDuplicateException("User already request to this requirement");
        }

        Request request = requestFactory.createRequest(user, requirement, command.brief());

        return requestRepository.save(request);
    }
}
