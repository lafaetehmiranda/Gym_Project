package org.project.infrastructure.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.project.domain.enums.StatusOnboarding;
import org.project.domain.model.OnboardingAluno;
import org.project.domain.repository.OnboardingAlunoRepository;
import org.project.infrastructure.persistence.entity.OnboardingAlunoEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class OnboardingAlunoRepositoryImpl
        implements PanacheRepositoryBase<OnboardingAlunoEntity, UUID>, OnboardingAlunoRepository {

    @Override
    public OnboardingAluno save(OnboardingAluno onboarding) {
        OnboardingAlunoEntity entity = new OnboardingAlunoEntity(
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
        persist(entity);
        return toDomain(entity);
    }

    @Override
    public Optional<OnboardingAluno> findDomainById(UUID id) {
        OnboardingAlunoEntity entity = find("id", id).firstResult();
        return entity != null ? Optional.of(toDomain(entity)) : Optional.empty();
    }

    @Override
    public List<OnboardingAluno> findAllDomain() {
        return streamAll()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<OnboardingAluno> findByUserId(UUID userId) {
        return list("userId", userId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<OnboardingAluno> findByStatus(StatusOnboarding status) {
        return list("status", status).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(UUID id) {
        return delete("id = ?1", id) > 0;
    }

    @Override
    public boolean existsById(UUID id) {
        return count("id", id) > 0;
    }

    private OnboardingAluno toDomain(OnboardingAlunoEntity entity) {
        return new OnboardingAluno(
                entity.getId(),
                entity.getUserId(),
                entity.getObjetivo(),
                entity.getExperiencia(),
                entity.getDisponibilidade(),
                entity.getPreferenciaTreino(),
                entity.getObservacoes(),
                entity.getCriadoEm(),
                entity.getAtualizadoEm(),
                entity.getStatus());
    }
}
