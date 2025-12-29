package org.project.interfaces.rest;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.project.domain.repository.PaymentRepository;

@Path("/webhooks/stripe")
public class StripeWebhookResource {

    @Inject
    PaymentRepository paymentRepository;

    @ConfigProperty(name = "stripe.webhook.secret")
    String webhookSecret;

    @POST
    @Transactional
    public Response handle(String payload,
            @HeaderParam("Stripe-Signature") String signature) {

        Event event;
        try {
            event = Webhook.constructEvent(payload, signature, webhookSecret);
        } catch (SignatureVerificationException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if ("payment_intent.succeeded".equals(event.getType())) {
            PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer()
                    .getObject().orElse(null);

            if (intent != null) {
                paymentRepository.findByGatewayPaymentId(intent.getId())
                        .ifPresent(payment -> payment.status = "PAID");
            }
        }

        return Response.ok().build();
    }
}
