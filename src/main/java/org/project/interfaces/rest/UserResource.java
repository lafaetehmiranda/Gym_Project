package org.project.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.project.application.dto.CreateUserRequest;
import org.project.application.dto.UserDTO;
import org.project.application.service.UserService;

import java.util.List;
import java.util.UUID;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Users", description = "User management endpoints")
public class UserResource {

    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Operation(summary = "Create a new user", description = "Creates a new user with the specified type")
    public Response create(CreateUserRequest request) {
        UserDTO created = userService.create(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @Operation(summary = "List all users", description = "Returns a list of all users")
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get user by ID", description = "Returns a single user by ID")
    public Response findById(@PathParam("id") UUID id) {
        return userService.findById(id)
                .map(user -> Response.ok(user).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user by ID")
    public Response deleteById(@PathParam("id") UUID id) {
        userService.deleteById(id);
        return Response.noContent().build();
    }
}
