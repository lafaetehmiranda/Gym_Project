package org.project.infrastructure.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class StripeClient {

    @ConfigProperty(name = "stripe.api.key")
    String apiKey;

    @PostConstruct
    void init() {
        Stripe.apiKey = apiKey;
    }

    public PaymentIntent createPayment(
            Long amount,
            String connectedAccountId,
            Long platformFee) throws StripeException {

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency("brl")
                .setApplicationFeeAmount(platformFee)
                .setTransferData(
                        PaymentIntentCreateParams.TransferData.builder()
                                .setDestination(connectedAccountId)
                                .build())
                .build();

        return PaymentIntent.create(params);
    }

    public com.stripe.model.Payout createPayout(String connectedAccountId, Long amount) throws StripeException {
        com.stripe.param.PayoutCreateParams params = com.stripe.param.PayoutCreateParams.builder()
                .setAmount(amount)
                .setCurrency("brl")
                .build();

        com.stripe.net.RequestOptions requestOptions = com.stripe.net.RequestOptions.builder()
                .setStripeAccount(connectedAccountId)
                .build();

        return com.stripe.model.Payout.create(params, requestOptions);
    }
}
