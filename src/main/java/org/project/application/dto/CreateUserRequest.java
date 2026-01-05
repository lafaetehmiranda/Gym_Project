package org.project.application.dto;

import org.project.domain.enums.UserType;

/**
 * Data Transfer Object for CreateUserRequest.
 */
public record CreateUserRequest(
                UserType userType,
                String name,
                String email,
                String phoneNumber) {
}
