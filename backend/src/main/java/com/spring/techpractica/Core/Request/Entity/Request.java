package com.spring.techpractica.Core.Request.Entity;

import com.spring.techpractica.Core.Request.model.RequestState;
import com.spring.techpractica.Core.Requirement.Entity.Requirement;
import com.spring.techpractica.Core.Shared.BaseEntity;
import com.spring.techpractica.Core.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@AllArgsConstructor
@Data
@Builder
@Table(name = "REQUESTS")
public class Request extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "requirement_id")
    private Requirement requirement;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "request_status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private RequestState requestStatus = RequestState.PENDING;

    @Column(name = "brief",
            length = 1000)
    private String brief;

    public Request() {
        requestStatus = RequestState.PENDING;
    }
}
