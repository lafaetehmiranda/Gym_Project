package org.project.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payouts")
public class PayoutEntity {

    @Id
    private UUID id;

    @Column(name = "trainer_id")
    private UUID trainerId;

    @Column(name = "gateway_payout_id")
    private String gatewayPayoutId;

    private Long amount;

    private String status;

    @Column(name = "created_at")
    private Instant createdAt;

    public PayoutEntity() {
    }

    public PayoutEntity(UUID id, UUID trainerId, String gatewayPayoutId, Long amount, String status,
            Instant createdAt) {
        this.id = id;
        this.trainerId = trainerId;
        this.gatewayPayoutId = gatewayPayoutId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(UUID trainerId) {
        this.trainerId = trainerId;
    }

    public String getGatewayPayoutId() {
        return gatewayPayoutId;
    }

    public void setGatewayPayoutId(String gatewayPayoutId) {
        this.gatewayPayoutId = gatewayPayoutId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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
