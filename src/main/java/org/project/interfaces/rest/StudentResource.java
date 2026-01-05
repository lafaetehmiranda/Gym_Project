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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.project.application.dto.CreateStudentRequest;
import org.project.application.dto.StudentDTO;
import org.project.application.dto.UpdateStudentRequest;
import org.project.application.service.StudentService;

import java.util.List;
import java.util.UUID;

/**
 * REST resource for Student management.
 */
@Path("/api/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Students", description = "Student management endpoints")
public class StudentResource {

    private final StudentService studentService;

    @Inject
    public StudentResource(StudentService studentService) {
        this.studentService = studentService;
    }

    @POST
    @Operation(summary = "Create a new student", description = "Creates a new student")
    public Response create(CreateStudentRequest request) {
        StudentDTO created = studentService.create(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @Operation(summary = "List all students", description = "Returns a list of all students")
    public List<StudentDTO> findAll() {
        return studentService.findAll();
    }

    @GET
    @Path("/active")
    @Operation(summary = "List active students", description = "Returns a list of all active students")
    public List<StudentDTO> findAllActive() {
        return studentService.findAllActive();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get student by ID", description = "Returns a single student by ID")
    public Response findById(@PathParam("id") UUID id) {
        return studentService.findById(id)
                .map(student -> Response.ok(student).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update student", description = "Updates a student by ID")
    public Response update(@PathParam("id") UUID id, UpdateStudentRequest request) {
        return studentService.update(id, request)
                .map(student -> Response.ok(student).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PATCH
    @Path("/{id}/deactivate")
    @Operation(summary = "Deactivate student", description = "Deactivates a student by ID")
    public Response deactivate(@PathParam("id") UUID id) {
        return studentService.deactivate(id)
                .map(student -> Response.ok(student).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PATCH
    @Path("/{id}/activate")
    @Operation(summary = "Activate student", description = "Activates a student by ID")
    public Response activate(@PathParam("id") UUID id) {
        return studentService.activate(id)
                .map(student -> Response.ok(student).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete student", description = "Deletes a student by ID")
    public Response deleteById(@PathParam("id") UUID id) {
        studentService.deleteById(id);
        return Response.noContent().build();
    }
}
