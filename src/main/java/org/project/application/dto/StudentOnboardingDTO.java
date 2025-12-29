package org.project.application.dto;

import org.project.domain.enums.ExperienceLevel;
import org.project.domain.enums.OnboardingStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record StudentOnboardingDTO(
        UUID id,
        UUID userId,
        String goal,
        ExperienceLevel experienceLevel,
        String availability,
        List<String> trainingPreferences,
        String observations,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        OnboardingStatus status) {
}
