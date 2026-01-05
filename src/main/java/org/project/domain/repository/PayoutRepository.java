package org.project.domain.repository;

import org.project.infrastructure.persistence.entity.PayoutEntity;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Payout persistence.
 */
public interface PayoutRepository {
    void persist(PayoutEntity payout);

    List<PayoutEntity> findByTrainerId(UUID trainerId);

    Long sumPayoutAmountByTrainerId(UUID trainerId);
}
