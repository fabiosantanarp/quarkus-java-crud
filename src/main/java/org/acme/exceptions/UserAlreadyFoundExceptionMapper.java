package org.acme.exceptions;

import java.util.Map;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UserAlreadyFoundExceptionMapper implements ExceptionMapper<UserAlreadyFoundException> {
    @Override
    public Response toResponse(UserAlreadyFoundException exception) {
        Map<String, String> errorBody = Map.of(
                "message", "User already exists");
        return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
                .entity(errorBody)
                .tag("UserAlreadyFound")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
