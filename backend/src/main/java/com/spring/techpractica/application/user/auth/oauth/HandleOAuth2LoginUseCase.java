package com.spring.techpractica.application.user.auth.oauth;

import com.spring.techpractica.core.user.Provider;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HandleOAuth2LoginUseCase {

    private final UserRepository userRepository;

    public void handle(OAuth2Command command) {
        if (command.isLinkMode()) {
            User user = userRepository.getOrThrowByID(command.sessionUserId());
            user.setGithubAccessToken(command.githubToken());
            user.setGithubConnected(true);
            user.setProviderId(command.providerId());
            userRepository.save(user);
            return;
        }

        userRepository.findByProviderAndProviderId(Provider.GITHUB, command.providerId())
                .or(() -> userRepository.findByEmail(command.email()))
                .ifPresentOrElse(
                        user -> updateExistingUser(command, user),
                        () -> createNewUser(command)
                );
    }

    private void updateExistingUser(OAuth2Command command, User user) {
        user.setGithubAccessToken(command.githubToken());
        user.setGithubConnected(true);
        user.setProvider(Provider.GITHUB);
        user.setProviderId(command.providerId());
        if (user.getEmail() == null) {
            user.setEmail(command.email());
        }
        userRepository.save(user);
    }

    private void createNewUser(OAuth2Command command) {
        User user = new User();
        user.setName(command.name());
        user.setEmail(command.email());
        user.setGithubAccessToken(command.githubToken());
        user.setGithubConnected(true);
        user.setProvider(Provider.GITHUB);
        user.setProviderId(command.providerId());
        userRepository.save(user);
    }
}
