package org.project.infrastructure.rest.dto;

import java.util.List;

/**
 * Response DTO for Gemini AI API.
 */
public record GeminiResponse(
                List<Candidate> candidates) {

        public record Candidate(
                        Content content,
                        String finishReason) {
        }

        public record Content(
                        List<Part> parts,
                        String role) {
        }

        public record Part(
                        String text) {
        }
}
