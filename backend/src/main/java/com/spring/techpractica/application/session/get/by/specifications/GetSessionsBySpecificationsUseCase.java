package com.spring.techpractica.application.session.get.by.specifications;

import com.spring.techpractica.core.session.entity.Session;
import com.spring.techpractica.core.session.SessionRepository;
import com.spring.techpractica.core.session.SessionStatus;
import com.spring.techpractica.core.session.service.SessionCodePolicy;
import com.spring.techpractica.infrastructure.jpa.session.SessionSpecifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@AllArgsConstructor
public class GetSessionsBySpecificationsUseCase {

    private final SessionRepository sessionRepository;

    public List<Session> execute(GetSessionsBySpecificationsCommand command) {

        if (SessionCodePolicy.isPrivateSessionCode(command.sessionCode())) {
            return List.of(sessionRepository.findBySessionCode(command.sessionCode()));
        }

        Specification<Session> specification = SessionSpecifications.buildDynamicSpecification(command.sessionName(), command.fieldName());

        Pageable pageable = buildPageable(command.page(), command.size(), command.sort());

        return sessionRepository.findAllWithSpecification(specification, pageable)
                .stream()
                .filter(session -> session.getStatus() != SessionStatus.DELETED
                        && session.getStatus() != SessionStatus.ENDED)
                .toList();
    }

    private Pageable buildPageable(int page, int size, String sort) {
        if (sort == null || sort.trim().isEmpty()) {
            return PageRequest.of(page, size);
        }

        Sort sortOrder = determineSortOrder(sort.toLowerCase());
        return PageRequest.of(page, size, sortOrder);
    }


    private Sort determineSortOrder(String sortType) {
        return switch (sortType) {
            case "asc", "ascending" -> Sort.by(Sort.Direction.ASC, "name");
            case "desc", "descending" -> Sort.by(Sort.Direction.DESC, "name");
            case "newestfirst" -> Sort.by(Sort.Direction.DESC, "atCreated");
            case "oldestfirst" -> Sort.by(Sort.Direction.ASC, "atCreated");
            default -> Sort.by(Sort.Direction.ASC, "name");
        };
    }
}


