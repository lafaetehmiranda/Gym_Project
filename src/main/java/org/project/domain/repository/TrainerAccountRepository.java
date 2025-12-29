package org.project.domain.repository;

import org.project.infrastructure.persistence.entity.TrainerAccountEntity;
import java.util.Optional;
import java.util.UUID;

public interface TrainerAccountRepository {
    Optional<TrainerAccountEntity> findByTrainerId(UUID trainerId);
}
