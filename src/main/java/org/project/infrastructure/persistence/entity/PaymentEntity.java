package org.project.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

/**
 * Database entity for Payment.
 */
@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    private UUID id;

    @Column(name = "student_id")
    private UUID studentId;

    @Column(name = "trainer_id")
    private UUID trainerId;

    private String gateway;

    @Column(name = "gateway_payment_id")
    private String gatewayPaymentId;

    @Column(name = "amount_total")
    private Long amountTotal;

    @Column(name = "platform_fee")
    private Long platformFee;

    @Column(name = "trainer_amount")
    private Long trainerAmount;

    private String status;

    @Column(name = "created_at")
    private Instant createdAt;

    public PaymentEntity() {
    }

    public PaymentEntity(UUID id, UUID studentId, UUID trainerId, String gateway, String gatewayPaymentId,
            Long amountTotal, Long platformFee, Long trainerAmount, String status, Instant createdAt) {
        this.id = id;
        this.studentId = studentId;
        this.trainerId = trainerId;
        this.gateway = gateway;
        this.gatewayPaymentId = gatewayPaymentId;
        this.amountTotal = amountTotal;
        this.platformFee = platformFee;
        this.trainerAmount = trainerAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public UUID getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(UUID trainerId) {
        this.trainerId = trainerId;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getGatewayPaymentId() {
        return gatewayPaymentId;
    }

    public void setGatewayPaymentId(String gatewayPaymentId) {
        this.gatewayPaymentId = gatewayPaymentId;
    }

    public Long getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(Long amountTotal) {
        this.amountTotal = amountTotal;
    }

    public Long getPlatformFee() {
        return platformFee;
    }

    public void setPlatformFee(Long platformFee) {
        this.platformFee = platformFee;
    }

    public Long getTrainerAmount() {
        return trainerAmount;
    }

    public void setTrainerAmount(Long trainerAmount) {
        this.trainerAmount = trainerAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
