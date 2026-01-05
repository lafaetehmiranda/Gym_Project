package org.project.domain.repository;

import org.project.domain.model.StudentOnboarding;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for StudentOnboarding persistence.
 */
public interface StudentOnboardingRepository {
    StudentOnboarding save(StudentOnboarding studentOnboarding);

    Optional<StudentOnboarding> findByUserId(UUID userId);

    List<StudentOnboarding> findAllDomain();

    boolean deleteByUserId(UUID userId);

    boolean existsById(UUID id);
}
