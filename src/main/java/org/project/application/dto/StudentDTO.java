package org.project.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record StudentDTO(
        UUID id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean active) {
}
