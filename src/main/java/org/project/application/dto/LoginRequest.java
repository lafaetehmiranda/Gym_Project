package org.project.application.dto;

/**
 * Data Transfer Object for login request.
 */
public record LoginRequest(String email, String password) {
}
