package org.acme.handlers;

import java.util.Set;

import org.acme.model.UserModel;
import org.acme.services.UserServiceInterface;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/users")
public class UserHandler {
    @Inject
    UserServiceInterface service;

    @GET()
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") String id) {
        UserModel user = this.service.findById(id);
        return Response.ok(user).build();
    }

    @GET()
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size) {
        Set<UserModel> users = this.service.findAll(page, size);
        return Response.ok(users).build();
    }

    @POST()
    public Response save(@Valid UserRequest userRequest) {
        UserModel userModel = userRequest.toUserModel();
        UserModel saved = this.service.save(userModel);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }
}
