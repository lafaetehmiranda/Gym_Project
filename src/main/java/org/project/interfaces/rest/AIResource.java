package org.project.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.project.application.service.GeminiService;

@Path("/chat")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AIResource {

    @Inject
    GeminiService geminiService;

    @POST
    public ChatResponse ask(ChatRequest request) {
        String answer = geminiService.ask(request.question());
        return new ChatResponse(answer);
    }

    public record ChatRequest(String question) {
    }

    public record ChatResponse(String answer) {
    }
}
