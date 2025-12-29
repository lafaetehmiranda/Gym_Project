package org.project.infrastructure.notification;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.project.infrastructure.persistence.entity.PaymentEntity;

@ApplicationScoped
public class EmailService {

        @Inject
        Mailer mailer;

        public void sendPaymentNotificationToTrainer(String trainerEmail, PaymentEntity payment) {
                String subject = "New Payment Received - Gym Platform";
                String body = String.format(
                                "Hello!\n\nYou received a new payment.\n" +
                                                "Amount: $ %.2f\n" +
                                                "Payment ID: %s\n" +
                                                "Status: %s\n\n" +
                                                "The amount is guaranteed in your platform account and you can request a withdrawal at any time.\n\n"
                                                +
                                                "Thank you for using our platform!",
                                (double) payment.getTrainerAmount() / 100,
                                payment.getId().toString(),
                                payment.getStatus());

                mailer.send(Mail.withText(trainerEmail, subject, body));
        }

        public void sendPaymentReminder(String studentEmail, String studentName, Long amount) {
                String subject = "Payment Reminder - Gym Platform";
                String body = String.format(
                                "Hello %s!\n\nWe identified that you haven't made the payment for your training session yet.\n"
                                                +
                                                "Amount: $ %.2f\n\n" +
                                                "Please complete the payment to continue accessing your sessions.\n\n" +
                                                "Best regards,\nGym Platform Team",
                                studentName,
                                (double) amount / 100);

                mailer.send(Mail.withText(studentEmail, subject, body));
        }
}
