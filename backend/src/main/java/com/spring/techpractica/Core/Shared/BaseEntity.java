package com.spring.techpractica.Core.Shared;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@Getter
public abstract class BaseEntity {
    @Id
    private UUID id;

    @Column(name = "at_created")
    private LocalDateTime atCreated;

    @Column(name = "at_modified")
    private LocalDateTime atModified;

    public BaseEntity() {
        this.id = UUID.randomUUID();
        this.atCreated = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (id == null) {
            this.id = UUID.randomUUID();
        }
        if (atCreated == null) {
            this.atCreated = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        atModified = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
