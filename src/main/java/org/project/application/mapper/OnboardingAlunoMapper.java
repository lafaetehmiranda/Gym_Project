package org.project.application.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.project.application.dto.OnboardingAlunoDTO;
import org.project.domain.model.OnboardingAluno;

@ApplicationScoped
public class OnboardingAlunoMapper {

    public OnboardingAlunoDTO toDTO(OnboardingAluno onboarding) {
        return new OnboardingAlunoDTO(
                onboarding.getId(),
                onboarding.getUserId(),
                onboarding.getObjetivo(),
                onboarding.getExperiencia(),
                onboarding.getDisponibilidade(),
                onboarding.getPreferenciaTreino(),
                onboarding.getObservacoes(),
                onboarding.getCriadoEm(),
                onboarding.getAtualizadoEm(),
                onboarding.getStatus());
    }
}
