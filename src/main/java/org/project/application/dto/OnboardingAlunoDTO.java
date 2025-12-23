package org.project.application.dto;

import org.project.domain.enums.ExperienciaAluno;
import org.project.domain.enums.StatusOnboarding;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OnboardingAlunoDTO(
        UUID id,
        UUID userId,
        String objetivo,
        ExperienciaAluno experiencia,
        String disponibilidade,
        List<String> preferenciaTreino,
        String observacoes,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm,
        StatusOnboarding status) {
}
