package org.project.application.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.project.domain.repository.PaymentRepository;
import org.project.domain.repository.TrainerAccountRepository;
import org.project.infrastructure.persistence.entity.PaymentEntity;
import org.project.infrastructure.stripe.StripeClient;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class PaymentService {

    @Inject
    StripeClient stripeClient;

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    TrainerAccountRepository trainerAccountRepository;

    @Transactional
    public PaymentEntity createPayment(UUID studentId, UUID trainerId, Long amount) {

        var trainerAccount = trainerAccountRepository
                .findByTrainerId(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer account not found"));

        long platformFee = amount * 10 / 100; // 10%
        long trainerAmount = amount - platformFee;

        PaymentIntent intent;
        try {
            intent = stripeClient.createPayment(
                    amount,
                    trainerAccount.gatewayAccountId,
                    platformFee);
        } catch (StripeException e) {
            throw new RuntimeException("Error creating Stripe payment", e);
        }

        PaymentEntity payment = new PaymentEntity();
        payment.id = UUID.randomUUID();
        payment.studentId = studentId;
        payment.trainerId = trainerId;
        payment.gateway = "STRIPE";
        payment.gatewayPaymentId = intent.getId();
        payment.amountTotal = amount;
        payment.platformFee = platformFee;
        payment.trainerAmount = trainerAmount;
        payment.status = "PENDING";
        payment.createdAt = Instant.now();

        paymentRepository.persist(payment);
        return payment;
    }
}
