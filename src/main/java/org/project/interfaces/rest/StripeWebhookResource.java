package org.project.interfaces.rest;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.project.application.service.PaymentService;
import org.jboss.logging.Logger;

/**
 * REST resource for handling Stripe webhooks.
 */
@Path("/webhooks/stripe")
public class StripeWebhookResource {

    private static final Logger LOG = Logger.getLogger(StripeWebhookResource.class);

    @ConfigProperty(name = "stripe.webhook.secret")
    String endpointSecret;

    @Inject
    PaymentService paymentService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response handleWebhook(String payload, @HeaderParam("Stripe-Signature") String sigHeader) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            LOG.error("Invalid Stripe signature", e);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        LOG.infof("Received Stripe event: %s", event.getType());

        if ("payment_intent.succeeded".equals(event.getType())) {
            PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
            if (paymentIntent != null) {
                paymentService.confirmPayment(paymentIntent.getId());
            }
        }

        return Response.ok().build();
    }
}
