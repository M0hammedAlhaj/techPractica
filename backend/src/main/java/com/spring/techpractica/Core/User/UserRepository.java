package com.spring.techpractica.Core.User;

import com.spring.techpractica.Core.Shared.BaseRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    void deleteAll();
}