package org.project.domain.model;

import org.project.domain.enums.ExperienceLevel;
import org.project.domain.enums.OnboardingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class StudentOnboarding {

    private final UUID id;
    private final UUID userId;
    private final String goal;
    private final ExperienceLevel experienceLevel;
    private final String availability;
    private final List<String> trainingPreferences;
    private final String observations;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final OnboardingStatus status;

    public StudentOnboarding(UUID id, UUID userId, String goal, ExperienceLevel experienceLevel,
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

    public static StudentOnboarding create(UUID userId, String goal, ExperienceLevel experienceLevel,
            String availability, List<String> trainingPreferences, String observations) {
        LocalDateTime now = LocalDateTime.now();
        return new StudentOnboarding(UUID.randomUUID(), userId, goal, experienceLevel, availability,
                trainingPreferences, observations, now, now, OnboardingStatus.PENDING);
    }

    public StudentOnboarding complete() {
        return new StudentOnboarding(this.id, this.userId, this.goal, this.experienceLevel,
                this.availability, this.trainingPreferences, this.observations,
                this.createdAt, LocalDateTime.now(), OnboardingStatus.COMPLETED);
    }

    public StudentOnboarding updateDetails(String goal, ExperienceLevel experienceLevel,
            String availability, List<String> trainingPreferences, String observations) {
        return new StudentOnboarding(this.id, this.userId, goal, experienceLevel, availability,
                trainingPreferences, observations, this.createdAt, LocalDateTime.now(), this.status);
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getGoal() {
        return goal;
    }

    public ExperienceLevel getExperienceLevel() {
        return experienceLevel;
    }

    public String getAvailability() {
        return availability;
    }

    public List<String> getTrainingPreferences() {
        return trainingPreferences;
    }

    public String getObservations() {
        return observations;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public OnboardingStatus getStatus() {
        return status;
    }
}
