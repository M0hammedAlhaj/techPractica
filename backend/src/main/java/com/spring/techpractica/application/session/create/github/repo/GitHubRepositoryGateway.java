package com.spring.techpractica.application.session.create.github.repo;

public interface GitHubRepositoryGateway {
    String createRepository(String token, String repoName, boolean isPrivate);
}