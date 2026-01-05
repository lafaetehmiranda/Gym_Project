package org.project.application.dto;

import org.project.domain.enums.UserType;

/**
 * Data Transfer Object for registration request.
 */
public record RegisterRequest(
        String name,
        String email,
        String password,
        String phoneNumber,
        UserType userType) {
}
