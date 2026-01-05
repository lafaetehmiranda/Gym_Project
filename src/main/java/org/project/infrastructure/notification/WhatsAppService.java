package org.project.infrastructure.notification;

import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

/**
 * Service for sending WhatsApp notifications.
 */
@ApplicationScoped
public class WhatsAppService {

    private static final Logger LOG = Logger.getLogger(WhatsAppService.class);

    public void sendMessage(String phoneNumber, String message) {
        LOG.infof("Sending WhatsApp message to %s: %s", phoneNumber, message);
    }

    public void sendPaymentReminder(String phoneNumber, String studentName, Long amount) {
        String message = String.format(
                "Hello %s! Payment reminder for your training session: $ %.2f. " +
                        "Access the platform to pay: https://gym-platform.com/pay",
                studentName,
                (double) amount / 100);
        sendMessage(phoneNumber, message);
    }
}
