package com.example.first_club.membership.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private String id;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Long updatedAt;

    @Version
    private Long version;

    @PrePersist
    protected void onCreate() {
        long epochMillis = Instant.now().toEpochMilli();
        createdAt = epochMillis;
        updatedAt = epochMillis;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now().toEpochMilli();
    }

    @PrePersist
    public void prePrePersist(){
        if(id == null){
            id = UUID.randomUUID().toString();
        }
    }
}

