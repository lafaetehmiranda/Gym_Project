package org.project.infrastructure.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.project.domain.repository.PaymentRepository;
import org.project.infrastructure.persistence.entity.PaymentEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository implementation for Payment.
 */
@ApplicationScoped
public class PaymentRepositoryImpl implements PanacheRepositoryBase<PaymentEntity, UUID>, PaymentRepository {
    @Override
    public void persist(PaymentEntity payment) {
        PanacheRepositoryBase.super.persist(payment);
    }

    @Override
    public Optional<PaymentEntity> findByGatewayPaymentId(String gatewayPaymentId) {
        return find("gatewayPaymentId", gatewayPaymentId).firstResultOptional();
    }

    @Override
    public Long sumPaidAmountByTrainerId(UUID trainerId) {
        return (Long) getEntityManager()
                .createQuery(
                        "SELECT SUM(p.trainerAmount) FROM PaymentEntity p WHERE p.trainerId = :trainerId AND p.status = 'PAID'")
                .setParameter("trainerId", trainerId)
                .getSingleResult();
    }
}
