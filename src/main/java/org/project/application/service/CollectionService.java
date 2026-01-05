package org.project.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.project.domain.repository.UserRepository;
import org.project.infrastructure.notification.EmailService;
import org.project.infrastructure.notification.WhatsAppService;
import org.jboss.logging.Logger;

import java.util.UUID;

/**
 * Service for handling collection and payment reminder business logic.
 */
@ApplicationScoped
public class CollectionService {

    private static final Logger LOG = Logger.getLogger(CollectionService.class);

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final WhatsAppService whatsAppService;

    @Inject
    public CollectionService(UserRepository userRepository, EmailService emailService,
            WhatsAppService whatsAppService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.whatsAppService = whatsAppService;
    }

    public void sendReminder(UUID studentId, Long amount) {
        userRepository.findDomainById(studentId).ifPresentOrElse(student -> {
            emailService.sendPaymentReminder(student.getEmail(), student.getName(), amount);

            String phoneNumber = student.getPhoneNumber();
            if (phoneNumber != null && !phoneNumber.isBlank()) {
                whatsAppService.sendPaymentReminder(phoneNumber, student.getName(), amount);
            }

            LOG.infof("Reminders sent to student %s (%s)", student.getName(), studentId);
        }, () -> {
            LOG.warnf("Student %s not found for reminder", studentId);
        });
    }
}
