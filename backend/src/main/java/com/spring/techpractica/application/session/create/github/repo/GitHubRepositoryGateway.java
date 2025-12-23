package com.spring.techpractica.application.session.create.github.repo;

public interface GitHubRepositoryGateway {
    void createRepository(String accessToken, String repoName, boolean isPrivate);
}