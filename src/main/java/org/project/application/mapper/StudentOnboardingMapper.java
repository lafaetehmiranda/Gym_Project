package org.project.application.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.project.application.dto.StudentOnboardingDTO;
import org.project.domain.model.StudentOnboarding;

/**
 * Mapper for converting between StudentOnboarding domain model and DTO.
 */
@ApplicationScoped
public class StudentOnboardingMapper {

    public StudentOnboardingDTO toDTO(StudentOnboarding onboarding) {
        return new StudentOnboardingDTO(
                onboarding.getId(),
                onboarding.getUserId(),
                onboarding.getGoal(),
                onboarding.getExperienceLevel(),
                onboarding.getAvailability(),
                onboarding.getTrainingPreferences(),
                onboarding.getObservations(),
                onboarding.getCreatedAt(),
                onboarding.getUpdatedAt(),
                onboarding.getStatus());
    }
}
