package org.project.infrastructure.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.project.domain.model.StudentOnboarding;
import org.project.domain.repository.StudentOnboardingRepository;
import org.project.infrastructure.persistence.entity.StudentOnboardingEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Repository implementation for Student onboarding.
 */
@ApplicationScoped
public class StudentOnboardingRepositoryImpl
        implements PanacheRepositoryBase<StudentOnboardingEntity, UUID>, StudentOnboardingRepository {

    @Override
    public StudentOnboarding save(StudentOnboarding onboarding) {
        StudentOnboardingEntity entity = toEntity(onboarding);
        if (existsById(entity.getId())) {
            getEntityManager().merge(entity);
        } else {
            persist(entity);
        }
        return toDomain(entity);
    }

    @Override
    public Optional<StudentOnboarding> findByUserId(UUID userId) {
        return find("userId", userId).firstResultOptional().map(this::toDomain);
    }

    @Override
    public List<StudentOnboarding> findAllDomain() {
        return streamAll().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean deleteByUserId(UUID userId) {
        return delete("userId", userId) > 0;
    }

    @Override
    public boolean existsById(UUID id) {
        return count("id", id) > 0;
    }

    private StudentOnboardingEntity toEntity(StudentOnboarding domain) {
        return new StudentOnboardingEntity(
                domain.getId(),
                domain.getUserId(),
                domain.getGoal(),
                domain.getExperienceLevel(),
                domain.getAvailability(),
                domain.getTrainingPreferences(),
                domain.getObservations(),
                domain.getCreatedAt(),
                domain.getUpdatedAt(),
                domain.getStatus());
    }

    private StudentOnboarding toDomain(StudentOnboardingEntity entity) {
        return new StudentOnboarding(
                entity.getId(),
                entity.getUserId(),
                entity.getGoal(),
                entity.getExperienceLevel(),
                entity.getAvailability(),
                entity.getTrainingPreferences(),
                entity.getObservations(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getStatus());
    }
}
