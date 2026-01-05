package org.project.domain.repository;

import org.project.domain.model.Trainee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Trainee persistence.
 */
public interface TraineeRepository {

    Trainee save(Trainee trainee);

    Optional<Trainee> findDomainById(UUID id);

    List<Trainee> findAllDomain();

    List<Trainee> findAllActive();

    List<Trainee> findByStudentId(UUID studentId);

    boolean deleteById(UUID id);

    boolean existsById(UUID id);
}
