package org.project.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TraineeDTO(
        UUID id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean active,
        UUID studentId,
        String description,
        Double value,
        String disponibilidade) {
}
