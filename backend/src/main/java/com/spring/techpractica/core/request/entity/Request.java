package com.spring.techpractica.core.request.entity;

import com.spring.techpractica.core.request.model.RequestState;
import com.spring.techpractica.core.requirement.entity.Requirement;
import com.spring.techpractica.core.shared.BaseEntity;
import com.spring.techpractica.core.user.User;
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

    @ManyToOne(fetch = FetchType.EAGER)
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
    public void approve() {
        requestStatus = RequestState.APPROVED;
    }
    public void reject() {
        requestStatus = RequestState.REJECTED;
    }

    public boolean isApproved() {
        return this.requestStatus == RequestState.APPROVED;
    }

    public boolean isRejected() {
        return this.requestStatus == RequestState.REJECTED;
    }

    public void delete() {
        this.requestStatus = RequestState.DELETED;
    }

}
