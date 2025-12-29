package org.project.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "trainer_accounts")
public class TrainerAccountEntity {

    @Id
    public UUID id;

    @Column(name = "trainer_id")
    public UUID trainerId;

    public String gateway;

    @Column(name = "gateway_account_id")
    public String gatewayAccountId;

    public String status;

    @Column(name = "created_at")
    public Instant createdAt;
}
