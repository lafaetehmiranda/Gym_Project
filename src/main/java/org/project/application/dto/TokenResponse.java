package org.project.application.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        Long expiresIn,
        UserDTO user) {
}
