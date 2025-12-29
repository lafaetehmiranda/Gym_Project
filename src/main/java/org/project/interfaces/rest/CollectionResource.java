package org.project.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.project.application.service.CollectionService;
import java.util.UUID;

@Path("/collections")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CollectionResource {

    @Inject
    CollectionService collectionService;

    @POST
    @Path("/remind/{studentId}")
    public Response sendReminder(@PathParam("studentId") UUID studentId, Long amount) {
        collectionService.sendReminder(studentId, amount);
        return Response.ok().build();
    }
}
