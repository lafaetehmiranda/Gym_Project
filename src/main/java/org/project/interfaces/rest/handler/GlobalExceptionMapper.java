package org.project.interfaces.rest.handler;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.util.List;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionMapper.class);

    @Inject
    MeterRegistry registry;

    @Override
    public Response toResponse(Throwable exception) {
        String exceptionType = exception.getClass().getSimpleName();

        LOG.error("Application error: " + exceptionType, exception);

        // Increment error counter
        registry.counter("application_errors_total",
                List.of(Tag.of("type", exceptionType)))
                .increment();

        if (exception instanceof WebApplicationException) {
            return ((WebApplicationException) exception).getResponse();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("An unexpected error occurred: " + exception.getMessage()))
                .build();
    }

    public record ErrorResponse(String message) {
    }
}
