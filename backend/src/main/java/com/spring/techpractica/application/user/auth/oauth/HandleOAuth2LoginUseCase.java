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

        userRepository.findByEmail(command.email())
                .ifPresentOrElse(
                        user -> {
                            user.setGithubAccessToken(command.githubToken());
                            userRepository.save(user);
                        },
                        () -> {
                            User user = new User();
                            user.setName(command.name());
                            user.setEmail(command.email());
                            user.setGithubAccessToken(command.githubToken());
                            user.setGithubConnected(true);
                            user.setProvider(Provider.GITHUB);
                            user.setProviderId(command.providerId());
                            userRepository.save(user);
                        }
                );
    }
}
