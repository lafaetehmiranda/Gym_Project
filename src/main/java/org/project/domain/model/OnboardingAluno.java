package org.project.domain.model;

import org.project.domain.enums.ExperienciaAluno;
import org.project.domain.enums.StatusOnboarding;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OnboardingAluno {

    private final UUID id;
    private final UUID userId;
    private final String objetivo;
    private final ExperienciaAluno experiencia;
    private final String disponibilidade;
    private final List<String> preferenciaTreino;
    private final String observacoes;
    private final LocalDateTime criadoEm;
    private final LocalDateTime atualizadoEm;
    private final StatusOnboarding status;

    public OnboardingAluno(UUID id, UUID userId, String objetivo, ExperienciaAluno experiencia,
            String disponibilidade, List<String> preferenciaTreino, String observacoes,
            LocalDateTime criadoEm, LocalDateTime atualizadoEm, StatusOnboarding status) {
        this.id = id;
        this.userId = userId;
        this.objetivo = objetivo;
        this.experiencia = experiencia;
        this.disponibilidade = disponibilidade;
        this.preferenciaTreino = preferenciaTreino;
        this.observacoes = observacoes;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
        this.status = status;
    }

    public static OnboardingAluno create(UUID userId, String objetivo, ExperienciaAluno experiencia,
            String disponibilidade, List<String> preferenciaTreino, String observacoes) {
        LocalDateTime now = LocalDateTime.now();
        return new OnboardingAluno(UUID.randomUUID(), userId, objetivo, experiencia, disponibilidade,
                preferenciaTreino, observacoes, now, now, StatusOnboarding.PENDENTE);
    }

    public OnboardingAluno complete() {
        return new OnboardingAluno(this.id, this.userId, this.objetivo, this.experiencia,
                this.disponibilidade, this.preferenciaTreino, this.observacoes,
                this.criadoEm, LocalDateTime.now(), StatusOnboarding.CONCLUIDO);
    }

    public OnboardingAluno updateDetails(String objetivo, ExperienciaAluno experiencia,
            String disponibilidade, List<String> preferenciaTreino, String observacoes) {
        return new OnboardingAluno(this.id, this.userId, objetivo, experiencia, disponibilidade,
                preferenciaTreino, observacoes, this.criadoEm, LocalDateTime.now(), this.status);
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public ExperienciaAluno getExperiencia() {
        return experiencia;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public List<String> getPreferenciaTreino() {
        return preferenciaTreino;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public StatusOnboarding getStatus() {
        return status;
    }
}
