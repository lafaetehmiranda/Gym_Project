package org.project.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.project.application.dto.CreateStudentOnboardingRequest;
import org.project.application.dto.UpdateStudentOnboardingRequest;
import org.project.application.service.StudentOnboardingService;

import java.util.UUID;

@Path("/onboarding")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentOnboardingResource {

    @Inject
    StudentOnboardingService service;

    @POST
    public Response create(CreateStudentOnboardingRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Path("/{userId}")
    public Response findByUserId(@PathParam("userId") UUID userId) {
        return service.findByUserId(userId)
                .map(onboarding -> Response.ok(onboarding).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    public Response findAll() {
        return Response.ok(service.findAll()).build();
    }

    @PUT
    @Path("/{userId}")
    public Response update(@PathParam("userId") UUID userId, UpdateStudentOnboardingRequest request) {
        return Response.ok(service.update(userId, request)).build();
    }

    @DELETE
    @Path("/{userId}")
    public Response delete(@PathParam("userId") UUID userId) {
        service.deleteByUserId(userId);
        return Response.noContent().build();
    }
}
