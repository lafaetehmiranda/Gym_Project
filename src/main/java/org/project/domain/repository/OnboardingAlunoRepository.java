package org.project.domain.repository;

import org.project.domain.enums.StatusOnboarding;
import org.project.domain.model.OnboardingAluno;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OnboardingAlunoRepository {

    OnboardingAluno save(OnboardingAluno onboarding);

    Optional<OnboardingAluno> findDomainById(UUID id);

    List<OnboardingAluno> findAllDomain();

    List<OnboardingAluno> findByUserId(UUID userId);

    List<OnboardingAluno> findByStatus(StatusOnboarding status);

    boolean deleteById(UUID id);

    boolean existsById(UUID id);
}
