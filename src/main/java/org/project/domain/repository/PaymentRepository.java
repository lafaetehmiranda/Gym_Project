package org.project.domain.repository;

import org.project.infrastructure.persistence.entity.PaymentEntity;
import java.util.Optional;

public interface PaymentRepository {
    void persist(PaymentEntity payment);

    Optional<PaymentEntity> findByGatewayPaymentId(String gatewayPaymentId);
}
