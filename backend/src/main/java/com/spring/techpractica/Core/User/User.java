package com.spring.techpractica.Core.User;

import com.spring.techpractica.Core.Notification.Entity.Notification;
import com.spring.techpractica.Core.Request.Entity.Request;
import com.spring.techpractica.Core.Role.Entity.Role;
import com.spring.techpractica.Core.Shared.BaseEntity;
import com.spring.techpractica.Core.SocialAccount.Entity.SocialAccount;
import com.spring.techpractica.Core.Task.Entity.Task;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "USERS")
public class User extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "brief")
    private String brief;

    @Column(name = "email")
    private String email;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.UNACTIVE_ACCOUNT;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Request> requests;

    @ManyToMany(mappedBy = "usersAssigned", fetch = FetchType.LAZY)
    private List<Task> tasksAssigned;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Notification> notifications;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USERS_SKILLS",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "technology_id", referencedColumnName = "id"))
    private Set<Technology> skills = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<SocialAccount> socialAccounts = new ArrayList<>();

    public void addInfo(String firstName, String lastName, String brief) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.brief = brief;
    }

    public boolean isProfileComplete() {
        return accountStatus.equals(AccountStatus.COMPLETE_PROFILE);
    }

    public boolean isInactiveAccount() {
        return accountStatus.equals(AccountStatus.UNACTIVE_ACCOUNT);
    }

    public void activate() {
        accountStatus = AccountStatus.ACTIVE_ACCOUNT;
    }

    public void deactivate() {
        accountStatus = AccountStatus.UNACTIVE_ACCOUNT;
    }

    public void completed() {
        accountStatus = AccountStatus.COMPLETE_PROFILE;
    }

    public void addSkills(Set<Technology> skills) {
        if (this.skills == null) {
            this.skills = new LinkedHashSet<>();
        }
        if (skills == null) {
            throw new IllegalArgumentException("socialAccounts is null");
        }
        this.skills.addAll(skills);
    }

    public void addSocialAccounts(List<SocialAccount> socialAccounts) {
        if (this.socialAccounts == null) {
            this.socialAccounts = new ArrayList<>();
        }
        if (socialAccounts == null) {
            throw new IllegalArgumentException("socialAccounts is null");
        }
        this.socialAccounts.addAll(socialAccounts);
    }
}
