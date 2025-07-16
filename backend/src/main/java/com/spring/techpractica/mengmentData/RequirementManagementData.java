package com.spring.techpractica.mengmentData;

import com.spring.techpractica.model.entity.Requirement;
import com.spring.techpractica.repository.RequirementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RequirementManagementData {

    private final RequirementRepository requirementRepository;

    public Requirement getRequirementBySessionIdAndSystem(Long sessionId, String category) {
        return requirementRepository.getRequirementByCategoryCategoryNameAndSessionSessionId(category, sessionId);
    }
}
