package org.project.infrastructure.rest.dto;

import java.util.List;

/**
 * Request DTO for Gemini AI API.
 */
public record GeminiRequest(
                List<Content> contents) {

        public record Content(
                        List<Part> parts) {
        }

        public record Part(
                        String text) {
        }
}
