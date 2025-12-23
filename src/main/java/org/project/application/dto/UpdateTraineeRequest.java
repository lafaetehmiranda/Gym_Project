package org.project.application.dto;

public record UpdateTraineeRequest(
        String name,
        String description,
        Double value,
        String disponibilidade) {
}
