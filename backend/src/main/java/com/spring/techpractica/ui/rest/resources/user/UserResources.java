package com.spring.techpractica.ui.rest.resources.user;

import com.spring.techpractica.core.user.User;
import com.spring.techpractica.ui.rest.resources.social.account.SocialAccountCollection;
import com.spring.techpractica.ui.rest.resources.technology.TechnologySummaryCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserResources {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String name;
    private final String email;
    private TechnologySummaryCollection skills;
    private SocialAccountCollection socialAccounts;
    private final String brief;
    private final List<String> roles;

    //Count of Sessions

    public UserResources(User user) {
        this.id = user.getId().toString();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.name = user.getName();
        this.email = user.getEmail();
        if (user.getSocialAccounts() != null) {
            this.skills = new TechnologySummaryCollection(user.getSkills().stream().toList());
        }
        if (user.getSocialAccounts() != null) {
            this.socialAccounts = new SocialAccountCollection(user.getSocialAccounts());
        }
        this.brief = user.getBrief();
        this.roles = user.getRoles().stream()
                .map(role -> role.getRoleType().toString())
                .toList();
    }
}