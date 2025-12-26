package com.spring.techpractica.application.user.auth.oauth;

import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HandleOAuth2LoginUseCase {

    private final UserRepository userRepository;

    public void handle(OAuth2Command command, User userAuth) {

        var byProvider = userRepository.findByProviderId(command.providerId());

        if (byProvider.isPresent()) {
            User user = byProvider.get();
            user.setGithubAccessToken(command.githubToken());
            userRepository.save(user);
            return;
        }

        if (userAuth != null) {

            if (userAuth.getGithubEmail() == null) {
                userAuth.setGithubEmail(command.email());
                userAuth.setProviderId(command.providerId());
                userAuth.setGithubAccessToken(command.githubToken());
                userRepository.save(userAuth);
                return;
            }

            userAuth.setGithubAccessToken(command.githubToken());
            userRepository.save(userAuth);
            return;
        }

        User user = new User();
        user.setName(command.name());
        user.setEmail(command.email());
        user.setGithubEmail(command.email());
        user.setGithubAccessToken(command.githubToken());
        user.setProviderId(command.providerId());
        userRepository.save(user);
    }
}
