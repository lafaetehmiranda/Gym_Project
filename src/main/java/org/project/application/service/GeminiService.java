package org.project.application.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.project.infrastructure.rest.GeminiClient;
import org.project.infrastructure.rest.dto.GeminiRequest;
import org.project.infrastructure.rest.dto.GeminiResponse;

import java.util.List;

/**
 * Service for interacting with Google's Gemini AI.
 */
@ApplicationScoped
public class GeminiService {

    @Inject
    @RestClient
    GeminiClient geminiClient;

    @Inject
    MeterRegistry registry;

    @ConfigProperty(name = "gemini.api.key")
    String apiKey;

    @ConfigProperty(name = "gemini.model", defaultValue = "gemini-1.5-flash")
    String model;

    public String ask(String question) {
        try {
            GeminiRequest.Part part = new GeminiRequest.Part(question);
            GeminiRequest.Content content = new GeminiRequest.Content(List.of(part));
            GeminiRequest request = new GeminiRequest(List.of(content));

            GeminiResponse response = geminiClient.generateContent(model, apiKey, request);

            if (response != null && response.candidates() != null && !response.candidates().isEmpty()) {
                registry.counter("gemini_requests_total", List.of(Tag.of("status", "success"))).increment();
                return response.candidates().get(0).content().parts().get(0).text();
            }
            registry.counter("gemini_requests_total", List.of(Tag.of("status", "empty_response"))).increment();
            return "No response from Gemini";
        } catch (jakarta.ws.rs.WebApplicationException e) {
            int status = e.getResponse().getStatus();
            registry.counter("gemini_requests_total",
                    List.of(Tag.of("status", "error"), Tag.of("http_code", String.valueOf(status))))
                    .increment();

            String errorBody = e.getResponse().readEntity(String.class);
            return "Error from Gemini (Status " + status + "): " + errorBody;
        } catch (Exception e) {
            registry.counter("gemini_requests_total", List.of(Tag.of("status", "unexpected_error"))).increment();
            return "Unexpected error: " + e.getMessage();
        }
    }
}
