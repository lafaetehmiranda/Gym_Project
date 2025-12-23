package org.project.application.dto;

import org.project.domain.enums.UserType;

import java.util.UUID;

public record UserDTO(
        UUID id,
        UserType userType) {
}
