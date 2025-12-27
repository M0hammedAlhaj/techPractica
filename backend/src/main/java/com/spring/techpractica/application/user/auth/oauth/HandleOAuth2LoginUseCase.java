package com.spring.techpractica.application.user.auth.oauth;

import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class HandleOAuth2LoginUseCase {

    private final UserRepository userRepository;

    public void handle(OAuth2Command command, UUID linkingUserId) {
        var byProvider = userRepository.findByProviderId(command.providerId());

        if (byProvider.isPresent()) {
            User user = byProvider.get();
            user.setGithubAccessToken(command.githubToken());
            userRepository.save(user);
            return;
        }

        if (linkingUserId != null) {
            var optionalUser = userRepository.findById(linkingUserId);
            if (optionalUser.isPresent()) {
                var userAuth = optionalUser.get();
                if (userAuth.getGithubEmail() == null) {
                    userAuth.setGitUserName(command.name());
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
        }

        User user = new User();
        user.setName(command.name());
        user.setGitUserName(command.name());
        user.setEmail(command.email());
        user.setGithubEmail(command.email());
        user.setGithubAccessToken(command.githubToken());
        user.setProviderId(command.providerId());
        userRepository.save(user);
    }
}
