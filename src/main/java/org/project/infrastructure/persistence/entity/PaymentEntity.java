package org.project.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    public UUID id;

    @Column(name = "student_id")
    public UUID studentId;

    @Column(name = "trainer_id")
    public UUID trainerId;

    public String gateway;

    @Column(name = "gateway_payment_id")
    public String gatewayPaymentId;

    @Column(name = "amount_total")
    public Long amountTotal;

    @Column(name = "platform_fee")
    public Long platformFee;

    @Column(name = "trainer_amount")
    public Long trainerAmount;

    public String status;

    @Column(name = "created_at")
    public Instant createdAt;
}
