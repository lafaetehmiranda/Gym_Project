package org.project.application.dto;

import org.project.domain.enums.ExperienciaAluno;

import java.util.List;

public record UpdateOnboardingRequest(
        String objetivo,
        ExperienciaAluno experiencia,
        String disponibilidade,
        List<String> preferenciaTreino,
        String observacoes) {
}
