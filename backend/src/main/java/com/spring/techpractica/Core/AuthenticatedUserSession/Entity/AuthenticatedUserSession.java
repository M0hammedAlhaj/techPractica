package com.spring.techpractica.Core.AuthenticatedUserSession.Entity;

import com.spring.techpractica.Core.Session.Entity.Session;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.AuthenticatedUserSession.model.SessionRole;
import com.spring.techpractica.Core.AuthenticatedUserSession.model.UserSessionId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AUTHENTICATED_USER_SESSION")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUserSession {
    @EmbeddedId
    private UserSessionId userSessionId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @MapsId("sessionId")
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    private Session session;

    @Enumerated(EnumType.STRING)
    @Column(name = "socoped_role")
    private SessionRole scopedRole;
}
