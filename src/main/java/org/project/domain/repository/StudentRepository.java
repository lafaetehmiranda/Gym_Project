package org.project.domain.repository;

import org.project.domain.model.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Student persistence.
 */
public interface StudentRepository {

    Student save(Student student);

    Optional<Student> findDomainById(UUID id);

    List<Student> findAllDomain();

    List<Student> findAllActive();

    boolean deleteById(UUID id);

    boolean existsById(UUID id);
}
