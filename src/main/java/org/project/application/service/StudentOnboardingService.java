package org.project.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.project.application.dto.CreateStudentOnboardingRequest;
import org.project.application.dto.StudentOnboardingDTO;
import org.project.application.dto.UpdateStudentOnboardingRequest;
import org.project.application.mapper.StudentOnboardingMapper;
import org.project.domain.model.StudentOnboarding;
import org.project.domain.repository.StudentOnboardingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class StudentOnboardingService {

    private final StudentOnboardingRepository repository;
    private final StudentOnboardingMapper mapper;

    @Inject
    public StudentOnboardingService(StudentOnboardingRepository repository, StudentOnboardingMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public StudentOnboardingDTO create(CreateStudentOnboardingRequest request) {
        StudentOnboarding onboarding = StudentOnboarding.create(
                request.userId(),
                request.goal(),
                request.experienceLevel(),
                request.availability(),
                request.trainingPreferences(),
                request.observations());
        return mapper.toDTO(repository.save(onboarding));
    }

    public Optional<StudentOnboardingDTO> findByUserId(UUID userId) {
        return repository.findByUserId(userId).map(mapper::toDTO);
    }

    public List<StudentOnboardingDTO> findAll() {
        return repository.findAllDomain().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public StudentOnboardingDTO update(UUID userId, UpdateStudentOnboardingRequest request) {
        StudentOnboarding onboarding = repository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Onboarding not found"));

        StudentOnboarding updated = onboarding.updateDetails(
                request.goal(),
                request.experienceLevel(),
                request.availability(),
                request.trainingPreferences(),
                request.observations());

        return mapper.toDTO(repository.save(updated));
    }

    @Transactional
    public void deleteByUserId(UUID userId) {
        repository.deleteByUserId(userId);
    }
}
