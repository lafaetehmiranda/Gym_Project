package org.project.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Student {

    private final UUID id;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final boolean active;

    public Student(UUID id, String name, LocalDateTime createdAt, LocalDateTime updatedAt, boolean active) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
    }

    public static Student create(String name) {
        LocalDateTime now = LocalDateTime.now();
        return new Student(UUID.randomUUID(), name, now, now, true);
    }

    public Student updateName(String newName) {
        return new Student(this.id, newName, this.createdAt, LocalDateTime.now(), this.active);
    }

    public Student deactivate() {
        return new Student(this.id, this.name, this.createdAt, LocalDateTime.now(), false);
    }

    public Student activate() {
        return new Student(this.id, this.name, this.createdAt, LocalDateTime.now(), true);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isActive() {
        return active;
    }
}
