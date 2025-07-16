package com.spring.techpractica.model.entity;

import com.spring.techpractica.model.RequestRequirementId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "REQUEST")
public class  Request {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "request_id")
    private int requestId;


    @Column(name = "request_status")
    private String requestStatus;


    @Column(name = "brief",
            length = 1000)
    private String brief;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id")
    private Session session;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "requirement_id")
     private Requirement requirement;
}
