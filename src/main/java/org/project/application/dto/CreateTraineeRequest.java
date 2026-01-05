package org.project.application.dto;

import java.util.UUID;

/**
 * Data Transfer Object for CreateTraineeRequest.
 */
public record CreateTraineeRequest(
        String name,
        UUID studentId,
        String description,
        Double value,
        String availability) {
}
