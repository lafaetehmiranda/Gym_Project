package org.project.application.dto;

/**
 * Data Transfer Object for updating Trainee.
 */
public record UpdateTraineeRequest(
        String name,
        String description,
        Double value,
        String availability) {
}
