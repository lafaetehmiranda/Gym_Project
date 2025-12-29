package org.project.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.project.application.service.WithdrawalService;
import java.util.UUID;

@Path("/withdrawals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WithdrawalResource {

    @Inject
    WithdrawalService withdrawalService;

    @GET
    @Path("/balance/{trainerId}")
    public Response getBalance(@PathParam("trainerId") UUID trainerId) {
        return Response.ok(withdrawalService.calculateAvailableBalance(trainerId)).build();
    }

    @POST
    @Path("/{trainerId}")
    public Response requestWithdrawal(@PathParam("trainerId") UUID trainerId, Long amount) {
        try {
            return Response.ok(withdrawalService.requestWithdrawal(trainerId, amount)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
