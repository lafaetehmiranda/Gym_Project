package org.project.application.dto;

import org.project.domain.enums.ExperienciaAluno;

import java.util.List;
import java.util.UUID;

public record CreateOnboardingRequest(
        UUID userId,
        String objetivo,
        ExperienciaAluno experiencia,
        String disponibilidade,
        List<String> preferenciaTreino,
        String observacoes) {
}
