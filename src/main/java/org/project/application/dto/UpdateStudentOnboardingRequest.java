package org.project.application.dto;

import org.project.domain.enums.ExperienceLevel;
import java.util.List;

public record UpdateStudentOnboardingRequest(
        String goal,
        ExperienceLevel experienceLevel,
        String availability,
        List<String> trainingPreferences,
        String observations) {
}
