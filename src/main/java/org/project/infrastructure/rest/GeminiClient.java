package org.project.infrastructure.rest;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.project.infrastructure.rest.dto.GeminiRequest;
import org.project.infrastructure.rest.dto.GeminiResponse;

/**
 * REST client for Gemini AI API.
 */
@RegisterRestClient(configKey = "gemini-api")
@Path("/v1/models")
public interface GeminiClient {

    @POST
    @Path("/{model}:generateContent")
    GeminiResponse generateContent(
            @jakarta.ws.rs.PathParam("model") String model,
            @QueryParam("key") String apiKey,
            GeminiRequest request);
}
