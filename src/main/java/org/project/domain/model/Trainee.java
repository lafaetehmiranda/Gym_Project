package org.project.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain model representing a trainee.
 */
public class Trainee {

    private final UUID id;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final boolean active;
    private final UUID studentId;
    private final String description;
    private final Double value;
    private final String availability;

    public Trainee(UUID id, String name, LocalDateTime createdAt, LocalDateTime updatedAt,
            boolean active, UUID studentId, String description, Double value, String availability) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
        this.studentId = studentId;
        this.description = description;
        this.value = value;
        this.availability = availability;
    }

    public static Trainee create(String name, UUID studentId, String description, Double value,
            String availability) {
        LocalDateTime now = LocalDateTime.now();
        return new Trainee(UUID.randomUUID(), name, now, now, true, studentId, description, value, availability);
    }

    public Trainee updateDetails(String name, String description, Double value, String availability) {
        return new Trainee(this.id, name, this.createdAt, LocalDateTime.now(), this.active,
                this.studentId, description, value, availability);
    }

    public Trainee deactivate() {
        return new Trainee(this.id, this.name, this.createdAt, LocalDateTime.now(), false,
                this.studentId, this.description, this.value, this.availability);
    }

    public Trainee activate() {
        return new Trainee(this.id, this.name, this.createdAt, LocalDateTime.now(), true,
                this.studentId, this.description, this.value, this.availability);
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

    public UUID getStudentId() {
        return studentId;
    }

    public String getDescription() {
        return description;
    }

    public Double getValue() {
        return value;
    }

    public String getAvailability() {
        return availability;
    }
}
