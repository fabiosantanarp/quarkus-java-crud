package org.acme.exceptions;

import java.util.Map;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<CustomException> {
    @Override
    public Response toResponse(CustomException exception) {
        Map<String, String> errorBody = Map.of(
                "message", "Internal server error");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .entity(errorBody)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
