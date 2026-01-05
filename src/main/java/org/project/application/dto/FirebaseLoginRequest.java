package org.project.application.dto;

import jakarta.validation.constraints.NotBlank;
import org.project.domain.enums.UserType;

/**
 * Data Transfer Object for Firebase login request.
 */
public record FirebaseLoginRequest(
        @NotBlank(message = "ID Token is required") String idToken,
        UserType userType) {
}
