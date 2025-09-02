package com.spring.techpractica.UI.Rest.Resources.User;

import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.UI.Rest.Resources.SocailAccount.SocialAccountCollection;
import com.spring.techpractica.UI.Rest.Resources.Technology.TechnologySummaryCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
    }
}