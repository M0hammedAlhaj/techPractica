package com.spring.techpractica.core.requirement;

import com.spring.techpractica.core.requirement.entity.Requirement;
import com.spring.techpractica.core.shared.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RequirementRepository extends BaseRepository<Requirement> {

    List<Requirement> findBySessionId(UUID sessionId);

    void delete(Requirement oldReq);
}
