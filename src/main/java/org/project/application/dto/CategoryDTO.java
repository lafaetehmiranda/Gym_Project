package org.project.application.dto;

import java.util.UUID;

/**
 * Data Transfer Object for Category.
 */
public record CategoryDTO(
                UUID id,
                String name,
                String value,
                String category,
                boolean active) {
}
