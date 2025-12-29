package org.project.application.dto;

import java.util.UUID;

public record CreateTraineeRequest(
                String name,
                UUID studentId,
                String description,
                Double value,
                String availability) {
}
