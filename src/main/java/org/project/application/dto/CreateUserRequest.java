package org.project.application.dto;

import org.project.domain.enums.UserType;

public record CreateUserRequest(
        UserType userType) {
}
