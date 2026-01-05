package org.project.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

/**
 * Database entity for Trainer account.
 */
@Entity
@Table(name = "trainer_accounts")
public class TrainerAccountEntity {

    @Id
    private UUID id;

    @Column(name = "trainer_id")
    private UUID trainerId;

    private String gateway;

    @Column(name = "gateway_account_id")
    private String gatewayAccountId;

    private String status;

    @Column(name = "created_at")
    private Instant createdAt;

    public TrainerAccountEntity() {
    }

    public TrainerAccountEntity(UUID id, UUID trainerId, String gateway, String gatewayAccountId, String status,
            Instant createdAt) {
        this.id = id;
        this.trainerId = trainerId;
        this.gateway = gateway;
        this.gatewayAccountId = gatewayAccountId;
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

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getGatewayAccountId() {
        return gatewayAccountId;
    }

    public void setGatewayAccountId(String gatewayAccountId) {
        this.gatewayAccountId = gatewayAccountId;
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
