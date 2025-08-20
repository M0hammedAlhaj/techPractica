package com.spring.techpractica.Core.User;

import com.spring.techpractica.Core.Notification.Entity.Notification;
import com.spring.techpractica.Core.Request.Entity.Request;
import com.spring.techpractica.Core.Role.Entity.Role;
import com.spring.techpractica.Core.Shared.BaseEntity;
import com.spring.techpractica.Core.SocialAccount.Entity.SocialAccount;
import com.spring.techpractica.Core.Task.Entity.Task;
import com.spring.techpractica.Core.Technology.Entity.Technology;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
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

    @Column(name = "email")
    private String email;

    @Column(name = "is_active")
    private boolean isActive;

    public void activate() {
        isActive = true;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USERS_SKILLS",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "technology_id", referencedColumnName = "id"))
    private List<Technology> userTechnologies;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<SocialAccount> socialAccounts;

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
}
