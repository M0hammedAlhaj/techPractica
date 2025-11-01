package com.spring.techpractica.infrastructure.jpa.user;

import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaUserRepository implements UserRepository {
    private final JpaUser jpaUser;

    @Override
    @Transactional
    public User save(User user) {
        return jpaUser.save(user);
    }

    @Override
    public User update(User user) {
        UUID userId = user.getId();
        if (jpaUser.existsById(userId)) {
            return jpaUser.save(user);
        }
        throw new ResourcesNotFoundException(userId);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaUser.findById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUser.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUser.findByEmail(email);
    }

    @Override
    public void deleteAll() {
        jpaUser.deleteAll();
    }

    @Override
    public List<User> findAllBySpecifications(Specification<User> specification, Pageable pageable) {
        return jpaUser.findAll(specification, pageable).getContent();
    }

    @Override
    public List<User> findAllByIds(Set<UUID> ids) {
        return jpaUser.findAllById(ids);
    }
}