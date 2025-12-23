package com.spring.techpractica.infrastructure.jpa.user;

import com.spring.techpractica.core.user.Provider;
import com.spring.techpractica.core.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaUser extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    User findByGithubAccessToken(String githubAccessToken);

    Optional<User> findByProviderAndProviderId(Provider provider, String providerId);
}