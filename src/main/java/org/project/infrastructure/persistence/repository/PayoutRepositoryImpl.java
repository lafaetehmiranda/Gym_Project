package org.project.infrastructure.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.project.domain.repository.PayoutRepository;
import org.project.infrastructure.persistence.entity.PayoutEntity;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PayoutRepositoryImpl implements PayoutRepository, PanacheRepositoryBase<PayoutEntity, UUID> {

    @Override
    public void persist(PayoutEntity payout) {
        PanacheRepositoryBase.super.persist(payout);
    }

    @Override
    public List<PayoutEntity> findByTrainerId(UUID trainerId) {
        return list("trainerId", trainerId);
    }

    @Override
    public Long sumPayoutAmountByTrainerId(UUID trainerId) {
        return (Long) getEntityManager()
                .createQuery(
                        "SELECT SUM(p.amount) FROM PayoutEntity p WHERE p.trainerId = :trainerId AND p.status = 'SUCCESS'")
                .setParameter("trainerId", trainerId)
                .getSingleResult();
    }
}
