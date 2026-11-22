package org.acme.exceptions;

import java.util.Map;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UserNotFoundExceptionMapper implements ExceptionMapper<UserNotFoundException> {
    @Override
    public Response toResponse(UserNotFoundException exception) {
        Map<String, String> errorBody = Map.of(
                "message", "User not found");
        return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                .entity(errorBody)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
