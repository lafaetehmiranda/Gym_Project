package org.project.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.project.application.dto.CreateOnboardingRequest;
import org.project.application.dto.OnboardingAlunoDTO;
import org.project.application.dto.UpdateOnboardingRequest;
import org.project.application.service.OnboardingAlunoService;
import org.project.domain.enums.StatusOnboarding;

import java.util.List;
import java.util.UUID;

@Path("/api/onboarding")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Onboarding", description = "Student onboarding management endpoints")
public class OnboardingResource {

    private final OnboardingAlunoService onboardingService;

    @Inject
    public OnboardingResource(OnboardingAlunoService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @POST
    @Operation(summary = "Create a new onboarding", description = "Creates a new student onboarding record")
    public Response create(CreateOnboardingRequest request) {
        OnboardingAlunoDTO created = onboardingService.create(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @Operation(summary = "List all onboarding records", description = "Returns a list of all onboarding records or filtered by user ID or status")
    public List<OnboardingAlunoDTO> findAll(
            @QueryParam("userId") UUID userId,
            @QueryParam("status") StatusOnboarding status) {
        if (userId != null) {
            return onboardingService.findByUserId(userId);
        }
        if (status != null) {
            return onboardingService.findByStatus(status);
        }
        return onboardingService.findAll();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get onboarding by ID", description = "Returns a single onboarding record by ID")
    public Response findById(@PathParam("id") UUID id) {
        return onboardingService.findById(id)
                .map(onboarding -> Response.ok(onboarding).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update onboarding", description = "Updates an onboarding record by ID")
    public Response update(@PathParam("id") UUID id, UpdateOnboardingRequest request) {
        return onboardingService.update(id, request)
                .map(onboarding -> Response.ok(onboarding).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PATCH
    @Path("/{id}/complete")
    @Operation(summary = "Complete onboarding", description = "Marks an onboarding record as completed")
    public Response complete(@PathParam("id") UUID id) {
        return onboardingService.complete(id)
                .map(onboarding -> Response.ok(onboarding).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete onboarding", description = "Deletes an onboarding record by ID")
    public Response deleteById(@PathParam("id") UUID id) {
        onboardingService.deleteById(id);
        return Response.noContent().build();
    }
}
