package org.project.infrastructure.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.project.domain.model.Trainee;
import org.project.domain.repository.TraineeRepository;
import org.project.infrastructure.persistence.entity.TraineeEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class TraineeRepositoryImpl implements PanacheRepositoryBase<TraineeEntity, UUID>, TraineeRepository {

    @Override
    public Trainee save(Trainee trainee) {
        TraineeEntity entity = new TraineeEntity(
                trainee.getId(),
                trainee.getName(),
                trainee.getCreatedAt(),
                trainee.getUpdatedAt(),
                trainee.isActive(),
                trainee.getStudentId(),
                trainee.getDescription(),
                trainee.getValue(),
                trainee.getAvailability());
        if (existsById(entity.getId())) {
            getEntityManager().merge(entity);
        } else {
            persist(entity);
        }
        return toDomain(entity);
    }

    @Override
    public Optional<Trainee> findDomainById(UUID id) {
        return findByIdOptional(id).map(this::toDomain);
    }

    @Override
    public List<Trainee> findAllDomain() {
        return streamAll()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Trainee> findAllActive() {
        return list("active", true).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Trainee> findByStudentId(UUID studentId) {
        return list("studentId", studentId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(UUID id) {
        return delete("id", id) > 0;
    }

    @Override
    public boolean existsById(UUID id) {
        return count("id", id) > 0;
    }

    private Trainee toDomain(TraineeEntity entity) {
        return new Trainee(
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.isActive(),
                entity.getStudentId(),
                entity.getDescription(),
                entity.getValue(),
                entity.getAvailability());
    }
}
