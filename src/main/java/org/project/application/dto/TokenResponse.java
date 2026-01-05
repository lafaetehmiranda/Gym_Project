package org.project.application.dto;

/**
 * Data Transfer Object for authentication token response.
 */
public record TokenResponse(
                String accessToken,
                String refreshToken,
                Long expiresIn,
                UserDTO user) {
}
