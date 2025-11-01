package com.spring.techpractica.application.session.request.create;

import com.spring.techpractica.core.request.entity.Request;
import com.spring.techpractica.core.request.RequestFactory;
import com.spring.techpractica.core.request.RequestRepository;
import com.spring.techpractica.core.requirement.entity.Requirement;
import com.spring.techpractica.core.requirement.RequirementRepository;
import com.spring.techpractica.core.shared.Exception.OperationDuplicateException;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.user.exception.IncompleteAccountException;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
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
