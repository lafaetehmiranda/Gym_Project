package org.project.application.dto;

import org.project.domain.enums.UserType;
import java.util.UUID;

/**
 * Data Transfer Object for User.
 */
public record UserDTO(
                UUID id,
                UserType userType,
                String name,
                String email,
                String phoneNumber) {
}
