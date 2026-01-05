package org.project.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Student.
 */
public record StudentDTO(
                UUID id,
                String name,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                boolean active) {
}
