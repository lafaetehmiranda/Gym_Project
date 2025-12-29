package org.project.domain.repository;

import org.project.infrastructure.persistence.entity.PaymentEntity;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    void persist(PaymentEntity payment);

    Optional<PaymentEntity> findByGatewayPaymentId(String gatewayPaymentId);

    Long sumPaidAmountByTrainerId(UUID trainerId);
}
