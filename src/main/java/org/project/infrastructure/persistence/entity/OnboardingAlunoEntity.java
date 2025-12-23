package org.project.infrastructure.persistence.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import org.project.domain.enums.ExperienciaAluno;
import org.project.domain.enums.StatusOnboarding;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "onboarding_aluno")
public class OnboardingAlunoEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String objetivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExperienciaAluno experiencia;

    @Column(nullable = false)
    private String disponibilidade;

    @ElementCollection
    @CollectionTable(name = "onboarding_preferencias", joinColumns = @JoinColumn(name = "onboarding_id"))
    @Column(name = "preferencia")
    private List<String> preferenciaTreino;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOnboarding status;

    public OnboardingAlunoEntity() {
    }

    public OnboardingAlunoEntity(UUID id, UUID userId, String objetivo, ExperienciaAluno experiencia,
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public ExperienciaAluno getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(ExperienciaAluno experiencia) {
        this.experiencia = experiencia;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public List<String> getPreferenciaTreino() {
        return preferenciaTreino;
    }

    public void setPreferenciaTreino(List<String> preferenciaTreino) {
        this.preferenciaTreino = preferenciaTreino;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public StatusOnboarding getStatus() {
        return status;
    }

    public void setStatus(StatusOnboarding status) {
        this.status = status;
    }
}
