package org.project.application.dto;

/**
 * Data Transfer Object for Google token verification request.
 */
public record GoogleTokenVerifyRequest(String idToken) {
}
