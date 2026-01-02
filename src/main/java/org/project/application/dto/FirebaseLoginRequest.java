package org.project.application.dto;

import jakarta.validation.constraints.NotBlank;

public record FirebaseLoginRequest(
        @NotBlank(message = "ID Token is required") String idToken) {
}
