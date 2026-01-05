package org.project.infrastructure.notification;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.project.infrastructure.persistence.entity.PaymentEntity;
import org.jboss.logging.Logger;

/**
 * Service for sending email notifications.
 */
@ApplicationScoped
public class EmailService {

        private static final Logger LOG = Logger.getLogger(EmailService.class);
        private static final String PLATFORM_NAME = "Gym Academy";
        private static final String SENDER_NAME = "Gym Academy Team";

        @Inject
        Mailer mailer;

        public void sendPaymentNotificationToTrainer(String trainerEmail, PaymentEntity payment) {
                String subject = String.format("[%s] New Payment Received", PLATFORM_NAME);

                try {
                        double amount = (double) payment.getTrainerAmount() / 100;
                        String body = String.format(
                                        "<html><body>" +
                                                        "<h2>Hello!</h2>" +
                                                        "<p>You received a new payment of <strong>$ %.2f</strong>.</p>"
                                                        +
                                                        "<ul>" +
                                                        "  <li><strong>Payment ID:</strong> %s</li>" +
                                                        "  <li><strong>Status:</strong> %s</li>" +
                                                        "</ul>" +
                                                        "<p>The amount is guaranteed in your platform account and you can request a withdrawal at any time.</p>"
                                                        +
                                                        "<p>Thank you for using our platform!</p>" +
                                                        "<p>Best regards,<br>%s</p>" +
                                                        "</body></html>",
                                        amount,
                                        payment.getId().toString(),
                                        payment.getStatus(),
                                        SENDER_NAME);

                        mailer.send(Mail.withHtml(trainerEmail, subject, body));
                        LOG.infof("Payment notification sent to trainer: %s", trainerEmail);
                } catch (Exception e) {
                        LOG.errorf(e, "Failed to send payment notification to trainer: %s", trainerEmail);
                }
        }

        public void sendPaymentReminder(String studentEmail, String studentName, Long amountCents) {
                String subject = String.format("[%s] Payment Reminder", PLATFORM_NAME);

                try {
                        double amount = (double) amountCents / 100;
                        String body = String.format(
                                        "<html><body>" +
                                                        "<h2>Hello %s!</h2>" +
                                                        "<p>We identified that you haven't made the payment for your training session yet.</p>"
                                                        +
                                                        "<p><strong>Amount: $ %.2f</strong></p>" +
                                                        "<p>Please complete the payment to continue accessing your sessions.</p>"
                                                        +
                                                        "<p>Best regards,<br>%s</p>" +
                                                        "</body></html>",
                                        studentName,
                                        amount,
                                        SENDER_NAME);

                        mailer.send(Mail.withHtml(studentEmail, subject, body));
                        LOG.infof("Payment reminder sent to student: %s", studentEmail);
                } catch (Exception e) {
                        LOG.errorf(e, "Failed to send payment reminder to student: %s", studentEmail);
                }
        }
}
