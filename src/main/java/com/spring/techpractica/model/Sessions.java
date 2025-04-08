package com.spring.techpractica.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "session")
public class Sessions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sessionId;
    private String sessionName;
    private String sessionDescription;
    private boolean isPrivate;
    @OneToMany
    private List<User> users= new ArrayList<>();

    public Sessions() {

    }

    public Sessions(int sessionId, String sessionName, String sessionDescription, boolean isPrivate) {
        this.sessionId = sessionId;
        this.sessionName = sessionName;
        this.sessionDescription = sessionDescription;
        this.isPrivate = isPrivate;
    }

}
