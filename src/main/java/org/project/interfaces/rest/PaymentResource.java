package org.project.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.project.application.service.PaymentService;
import org.project.infrastructure.persistence.entity.PaymentEntity;

import java.util.UUID;

@Path("/payments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PaymentResource {

    @Inject
    PaymentService paymentService;

    @POST
    public PaymentEntity create(PaymentRequest request) {
        return paymentService.createPayment(
                request.studentId(),
                request.trainerId(),
                request.amount());
    }

    public record PaymentRequest(
            UUID studentId,
            UUID trainerId,
            Long amount) {
    }
}
