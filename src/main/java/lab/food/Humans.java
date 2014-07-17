package lab.food;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lab.support.PATCH;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static javax.ws.rs.core.Response.Status;

@Controller
@Path("/humans")
@Produces(MediaType.APPLICATION_JSON)
public class Humans {
    @GET
    public Response retrieveAll() {
        return Response.ok(ImmutableList.of()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Context UriInfo uriInfo, Human.Builder humanBuilder) {
        humanBuilder.setId(1L);
        Human human = humanBuilder.build();
        URI location = uriInfo.getAbsolutePathBuilder().path("{id}").build(human.getId());
        return Response.created(location).entity(human).build();
    }

    @GET
    @Path("/{id}")
    public Response retrieve(@PathParam("id") long id) {
        return Response.status(Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{id}")
    public Response createOrReplace(@PathParam("id") long id, Human.Builder humanBuilder) {
        if (id == humanBuilder.getId()) {
            return Response.ok(humanBuilder.build()).build();
        } else {
            return Response.status(Status.BAD_REQUEST).build();
        }
    }

    @PATCH
    @Path("/{id}")
    public Response update(@PathParam("id") long id, Human.Builder updates) {
        if (updates.getId() != null && id != updates.getId()) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        Human.Builder currentState = Human.builder().setId(id).setName("Albert").setFavorites(ImmutableSet.of(Favorite.of(1L, 8L, 3.5)));
        return Response.ok(currentState.merge(updates).build()).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) {
        return Response.noContent().build();
    }
}
