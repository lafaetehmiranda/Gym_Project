package org.project.application.dto;

import org.project.domain.enums.UserType;

public record RegisterRequest(
        String name,
        String email,
        String password,
        UserType userType) {
}
