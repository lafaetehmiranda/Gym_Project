package org.project.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.project.domain.repository.PaymentRepository;
import org.project.domain.repository.PayoutRepository;
import org.project.domain.repository.TrainerAccountRepository;
import org.project.infrastructure.persistence.entity.PayoutEntity;
import org.project.infrastructure.persistence.entity.TrainerAccountEntity;
import org.project.infrastructure.stripe.StripeClient;
import com.stripe.exception.StripeException;

import java.time.Instant;
import java.util.UUID;

/**
 * Service for handling trainer withdrawal business logic.
 */
@ApplicationScoped
public class WithdrawalService {

    private final PaymentRepository paymentRepository;
    private final PayoutRepository payoutRepository;
    private final TrainerAccountRepository trainerAccountRepository;
    private final StripeClient stripeClient;

    @Inject
    public WithdrawalService(PaymentRepository paymentRepository, PayoutRepository payoutRepository,
            TrainerAccountRepository trainerAccountRepository, StripeClient stripeClient) {
        this.paymentRepository = paymentRepository;
        this.payoutRepository = payoutRepository;
        this.trainerAccountRepository = trainerAccountRepository;
        this.stripeClient = stripeClient;
    }

    public Long calculateAvailableBalance(UUID trainerId) {
        Long totalPaid = paymentRepository.sumPaidAmountByTrainerId(trainerId);
        Long totalWithdrawn = payoutRepository.sumPayoutAmountByTrainerId(trainerId);

        if (totalPaid == null)
            totalPaid = 0L;
        if (totalWithdrawn == null)
            totalWithdrawn = 0L;

        return totalPaid - totalWithdrawn;
    }

    @Transactional
    public PayoutEntity requestWithdrawal(UUID trainerId, Long amount) {
        Long available = calculateAvailableBalance(trainerId);
        if (amount > available) {
            throw new RuntimeException("Insufficient balance");
        }

        TrainerAccountEntity trainerAccount = trainerAccountRepository.findByTrainerId(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer account not found"));

        try {
            var stripePayout = stripeClient.createPayout(trainerAccount.getGatewayAccountId(), amount);

            PayoutEntity payout = new PayoutEntity(
                    UUID.randomUUID(),
                    trainerId,
                    stripePayout.getId(),
                    amount,
                    "SUCCESS",
                    Instant.now());

            payoutRepository.persist(payout);
            return payout;
        } catch (StripeException e) {
            throw new RuntimeException("Error creating payout", e);
        }
    }
}
