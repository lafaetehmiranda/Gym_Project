package org.project.infrastructure.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.project.domain.repository.TrainerAccountRepository;
import org.project.infrastructure.persistence.entity.TrainerAccountEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository implementation for Trainer account.
 */
@ApplicationScoped
public class TrainerAccountRepositoryImpl
        implements PanacheRepositoryBase<TrainerAccountEntity, UUID>, TrainerAccountRepository {
    @Override
    public Optional<TrainerAccountEntity> findByTrainerId(UUID trainerId) {
        return find("trainerId", trainerId).firstResultOptional();
    }
}
