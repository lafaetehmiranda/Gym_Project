package org.project.application.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.project.domain.repository.PaymentRepository;
import org.project.domain.repository.TrainerAccountRepository;
import org.project.domain.repository.UserRepository;
import org.project.infrastructure.persistence.entity.PaymentEntity;
import org.project.infrastructure.persistence.entity.TrainerAccountEntity;
import org.project.infrastructure.stripe.StripeClient;
import org.project.infrastructure.notification.EmailService;

import java.time.Instant;
import java.util.UUID;

/**
 * Service for handling payment and transaction business logic.
 */
@ApplicationScoped
public class PaymentService {

    private final StripeClient stripeClient;
    private final PaymentRepository paymentRepository;
    private final TrainerAccountRepository trainerAccountRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Inject
    public PaymentService(StripeClient stripeClient, PaymentRepository paymentRepository,
            TrainerAccountRepository trainerAccountRepository, UserRepository userRepository,
            EmailService emailService) {
        this.stripeClient = stripeClient;
        this.paymentRepository = paymentRepository;
        this.trainerAccountRepository = trainerAccountRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Transactional
    public PaymentEntity createPayment(UUID studentId, UUID trainerId, Long amount) {
        TrainerAccountEntity trainerAccount = trainerAccountRepository
                .findByTrainerId(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer account not found"));

        long platformFee = amount * 10 / 100;
        long trainerAmount = amount - platformFee;

        PaymentIntent intent;
        try {
            intent = stripeClient.createPayment(
                    amount,
                    trainerAccount.getGatewayAccountId(),
                    platformFee);
        } catch (StripeException e) {
            throw new RuntimeException("Error creating Stripe payment", e);
        }

        PaymentEntity payment = new PaymentEntity(
                UUID.randomUUID(),
                studentId,
                trainerId,
                "STRIPE",
                intent.getId(),
                amount,
                platformFee,
                trainerAmount,
                "PENDING",
                Instant.now());

        paymentRepository.persist(payment);
        return payment;
    }

    @Transactional
    public void confirmPayment(String gatewayPaymentId) {
        paymentRepository.findByGatewayPaymentId(gatewayPaymentId).ifPresent(payment -> {
            if ("PAID".equals(payment.getStatus()))
                return;

            payment.setStatus("PAID");

            userRepository.findDomainById(payment.getTrainerId()).ifPresent(trainer -> {
                emailService.sendPaymentNotificationToTrainer(trainer.getEmail(), payment);
            });
        });
    }
}
