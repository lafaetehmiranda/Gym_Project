package org.project.infrastructure.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.project.domain.model.Student;
import org.project.domain.repository.StudentRepository;
import org.project.infrastructure.persistence.entity.StudentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class StudentRepositoryImpl implements PanacheRepositoryBase<StudentEntity, UUID>, StudentRepository {

    @Override
    public Student save(Student student) {
        StudentEntity entity = new StudentEntity(
                student.getId(),
                student.getName(),
                student.getCreatedAt(),
                student.getUpdatedAt(),
                student.isActive());
        persist(entity);
        return toDomain(entity);
    }

    @Override
    public Optional<Student> findDomainById(UUID id) {
        StudentEntity entity = find("id", id).firstResult();
        return entity != null ? Optional.of(toDomain(entity)) : Optional.empty();
    }

    @Override
    public List<Student> findAllDomain() {
        return streamAll()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findAllActive() {
        return list("active", true).stream()
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

    private Student toDomain(StudentEntity entity) {
        return new Student(
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.isActive());
    }
}
