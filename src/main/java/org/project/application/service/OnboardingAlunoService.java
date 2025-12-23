package org.project.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.project.application.dto.CreateOnboardingRequest;
import org.project.application.dto.OnboardingAlunoDTO;
import org.project.application.dto.UpdateOnboardingRequest;
import org.project.application.mapper.OnboardingAlunoMapper;
import org.project.domain.enums.StatusOnboarding;
import org.project.domain.model.OnboardingAluno;
import org.project.domain.repository.OnboardingAlunoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class OnboardingAlunoService {

    private final OnboardingAlunoRepository onboardingRepository;
    private final OnboardingAlunoMapper onboardingMapper;

    @Inject
    public OnboardingAlunoService(OnboardingAlunoRepository onboardingRepository,
            OnboardingAlunoMapper onboardingMapper) {
        this.onboardingRepository = onboardingRepository;
        this.onboardingMapper = onboardingMapper;
    }

    @Transactional
    public OnboardingAlunoDTO create(CreateOnboardingRequest request) {
        OnboardingAluno onboarding = OnboardingAluno.create(
                request.userId(),
                request.objetivo(),
                request.experiencia(),
                request.disponibilidade(),
                request.preferenciaTreino(),
                request.observacoes());
        OnboardingAluno savedOnboarding = onboardingRepository.save(onboarding);
        return onboardingMapper.toDTO(savedOnboarding);
    }

    public Optional<OnboardingAlunoDTO> findById(UUID id) {
        return onboardingRepository.findDomainById(id)
                .map(onboardingMapper::toDTO);
    }

    public List<OnboardingAlunoDTO> findAll() {
        return onboardingRepository.findAllDomain().stream()
                .map(onboardingMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<OnboardingAlunoDTO> findByUserId(UUID userId) {
        return onboardingRepository.findByUserId(userId).stream()
                .map(onboardingMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<OnboardingAlunoDTO> findByStatus(StatusOnboarding status) {
        return onboardingRepository.findByStatus(status).stream()
                .map(onboardingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<OnboardingAlunoDTO> update(UUID id, UpdateOnboardingRequest request) {
        return onboardingRepository.findDomainById(id)
                .map(onboarding -> onboarding.updateDetails(
                        request.objetivo(),
                        request.experiencia(),
                        request.disponibilidade(),
                        request.preferenciaTreino(),
                        request.observacoes()))
                .map(onboardingRepository::save)
                .map(onboardingMapper::toDTO);
    }

    @Transactional
    public Optional<OnboardingAlunoDTO> complete(UUID id) {
        return onboardingRepository.findDomainById(id)
                .map(OnboardingAluno::complete)
                .map(onboardingRepository::save)
                .map(onboardingMapper::toDTO);
    }

    @Transactional
    public void deleteById(UUID id) {
        onboardingRepository.deleteById(id);
    }
}
