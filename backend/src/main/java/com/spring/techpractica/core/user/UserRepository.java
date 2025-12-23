package com.spring.techpractica.core.user;

import com.spring.techpractica.core.shared.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRepository extends BaseRepository<User> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    void deleteAll();

    List<User> findAllBySpecifications(Specification<User> specification, Pageable pageable);

    List<User> findAllByIds(Set<UUID> ids);

    Optional<User> findByProviderAndProviderId(Provider provider, String providerId);
}