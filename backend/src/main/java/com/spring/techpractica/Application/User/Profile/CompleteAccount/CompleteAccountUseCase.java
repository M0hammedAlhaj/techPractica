package com.spring.techpractica.Application.User.Profile.CompleteAccount;

import com.spring.techpractica.Core.Shared.Exception.OperationDuplicateException;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.SocialAccount.Entity.SocialAccount;
import com.spring.techpractica.Core.SocialAccount.SocialAccountFactory;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import com.spring.techpractica.Core.Technology.TechnologyRepository;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.User.UserRepository;
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
