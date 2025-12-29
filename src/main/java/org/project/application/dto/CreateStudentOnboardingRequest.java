package org.project.application.dto;

import org.project.domain.enums.ExperienceLevel;
import java.util.List;
import java.util.UUID;

public record CreateStudentOnboardingRequest(
        UUID userId,
        String goal,
        ExperienceLevel experienceLevel,
        String availability,
        List<String> trainingPreferences,
        String observations) {
}
