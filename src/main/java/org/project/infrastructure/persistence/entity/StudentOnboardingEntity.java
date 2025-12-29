package org.project.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.project.domain.enums.ExperienceLevel;
import org.project.domain.enums.OnboardingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "student_onboarding")
public class StudentOnboardingEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String goal;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience_level", nullable = false)
    private ExperienceLevel experienceLevel;

    @Column(nullable = false)
    private String availability;

    @ElementCollection
    @CollectionTable(name = "onboarding_preferences", joinColumns = @JoinColumn(name = "onboarding_id"))
    @Column(name = "preference")
    private List<String> trainingPreferences;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OnboardingStatus status;

    public StudentOnboardingEntity() {
    }

    public StudentOnboardingEntity(UUID id, UUID userId, String goal, ExperienceLevel experienceLevel,
            String availability, List<String> trainingPreferences, String observations,
            LocalDateTime createdAt, LocalDateTime updatedAt, OnboardingStatus status) {
        this.id = id;
        this.userId = userId;
        this.goal = goal;
        this.experienceLevel = experienceLevel;
        this.availability = availability;
        this.trainingPreferences = trainingPreferences;
        this.observations = observations;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public ExperienceLevel getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(ExperienceLevel experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public List<String> getTrainingPreferences() {
        return trainingPreferences;
    }

    public void setTrainingPreferences(List<String> trainingPreferences) {
        this.trainingPreferences = trainingPreferences;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public OnboardingStatus getStatus() {
        return status;
    }

    public void setStatus(OnboardingStatus status) {
        this.status = status;
    }
}
