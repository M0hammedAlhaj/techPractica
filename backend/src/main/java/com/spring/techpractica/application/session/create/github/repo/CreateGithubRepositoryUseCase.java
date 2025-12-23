package com.spring.techpractica.application.session.create.github.repo;

import com.spring.techpractica.application.session.create.github.token.GitHubTokenValidator;
import com.spring.techpractica.core.shared.Exception.GitHubTokenInvalidException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateGithubRepositoryUseCase {

    private final GitHubRepositoryGateway gateway;
    private final GitHubTokenValidator  tokenValidator;

    public void createRepository(String accessToken, String repoName, boolean isPrivate) {

        if(!tokenValidator.isValid(accessToken)){
            throw new GitHubTokenInvalidException();
        }
        gateway.createRepository(accessToken, repoName, isPrivate);
    }
}