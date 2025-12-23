package org.project.application.dto;

import java.util.UUID;

public record CategoryDTO(
        UUID id,
        String name,
        String value,
        String category,
        boolean active) {
}
