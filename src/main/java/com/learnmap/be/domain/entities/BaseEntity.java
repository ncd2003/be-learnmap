package com.learnmap.be.domain.entities;

import com.learnmap.be.domain.utils.DateUtils;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "active")
    private Boolean active = true;

    @PrePersist
    protected void onCreate() {createdAt = DateUtils.getNow();}

    @PreUpdate
    protected void onUpdate() {
        updatedAt = DateUtils.getNow();
    }
}
