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
import org.project.application.dto.CreateTraineeRequest;
import org.project.application.dto.TraineeDTO;
import org.project.application.dto.UpdateTraineeRequest;
import org.project.application.service.TraineeService;

import java.util.List;
import java.util.UUID;

@Path("/api/trainees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Trainees", description = "Trainee management endpoints")
public class TraineeResource {

    private final TraineeService traineeService;

    @Inject
    public TraineeResource(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @POST
    @Operation(summary = "Create a new trainee", description = "Creates a new trainee")
    public Response create(CreateTraineeRequest request) {
        TraineeDTO created = traineeService.create(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @Operation(summary = "List all trainees", description = "Returns a list of all trainees or filtered by student ID")
    public List<TraineeDTO> findAll(@QueryParam("studentId") UUID studentId) {
        if (studentId != null) {
            return traineeService.findByStudentId(studentId);
        }
        return traineeService.findAll();
    }

    @GET
    @Path("/active")
    @Operation(summary = "List active trainees", description = "Returns a list of all active trainees")
    public List<TraineeDTO> findAllActive() {
        return traineeService.findAllActive();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get trainee by ID", description = "Returns a single trainee by ID")
    public Response findById(@PathParam("id") UUID id) {
        return traineeService.findById(id)
                .map(trainee -> Response.ok(trainee).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update trainee", description = "Updates a trainee by ID")
    public Response update(@PathParam("id") UUID id, UpdateTraineeRequest request) {
        return traineeService.update(id, request)
                .map(trainee -> Response.ok(trainee).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PATCH
    @Path("/{id}/deactivate")
    @Operation(summary = "Deactivate trainee", description = "Deactivates a trainee by ID")
    public Response deactivate(@PathParam("id") UUID id) {
        return traineeService.deactivate(id)
                .map(trainee -> Response.ok(trainee).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PATCH
    @Path("/{id}/activate")
    @Operation(summary = "Activate trainee", description = "Activates a trainee by ID")
    public Response activate(@PathParam("id") UUID id) {
        return traineeService.activate(id)
                .map(trainee -> Response.ok(trainee).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete trainee", description = "Deletes a trainee by ID")
    public Response deleteById(@PathParam("id") UUID id) {
        traineeService.deleteById(id);
        return Response.noContent().build();
    }
}
