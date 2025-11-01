package com.spring.techpractica.application.user.profile.complete;

import com.spring.techpractica.core.shared.Exception.OperationDuplicateException;
import com.spring.techpractica.core.shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.core.social.account.entity.SocialAccount;
import com.spring.techpractica.core.social.account.SocialAccountFactory;
import com.spring.techpractica.core.technology.entity.Technology;
import com.spring.techpractica.core.technology.TechnologyRepository;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompleteAccountUseCase {

    private final UserRepository userRepository;
    private final TechnologyRepository technologyRepository;
    private final SocialAccountFactory socialAccountFactory;

    @Transactional
    public User execute(CompleteAccountCommand command) {

        User user = userRepository.getOrThrowByID(command.userId());

        if (user.isProfileComplete()) {
            throw new OperationDuplicateException("Complete Account");
        }

        Set<Technology> technologies = command.skillsIds().stream()
                .map(id -> technologyRepository.findById(id)
                        .orElseThrow(() -> new ResourcesNotFoundException(id)))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        List<SocialAccount> socialAccounts = command.socialAccountRequests().stream().map(
                request -> {

                    SocialAccount created = socialAccountFactory
                            .create(request.platformName(), request.profileUrl(), user);

                    created.setUser(user);

                    return created;
                }
        ).collect(Collectors.toCollection(ArrayList::new));

        user.addSkills(technologies);

        user.addSocialAccounts(socialAccounts);

        user.addInfo(command.firstName(), command.lastName(), command.brief());

        user.completed();
        return userRepository.update(user);
    }
}
