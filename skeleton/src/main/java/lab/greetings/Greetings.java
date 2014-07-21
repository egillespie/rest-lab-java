package lab.greetings;

import lab.support.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Controller
@Path("/greetings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Greetings {
    private final GreetingDao greetingDao;

    @Autowired
    public Greetings(GreetingDao greetingDao) {
        this.greetingDao = greetingDao;
    }

    @POST
    @Path("/")
    public Response create(@Context UriInfo uriInfo, Greeting.Builder greetingBuilder) {
        Greeting greeting = greetingDao.create(greetingBuilder);
        URI location = uriInfo.getAbsolutePathBuilder().path("{arg1}").build(greeting.getId());
        return Response.created(location).entity(greeting).build();
    }

    @PUT
    @Path("/{greetingId}")
    public Response update(@PathParam("greetingId") int greetingId, Greeting.Builder greetingBuilder) {
        try {
            Greeting greeting = greetingBuilder.setId(greetingId).build();
            greetingDao.update(greeting);
            return Response.ok(greeting).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{greetingId}")
    @Consumes(MediaType.WILDCARD)
    public Response delete(@PathParam("greetingId") int greetingId) {
        try {
            greetingDao.delete(greetingId);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/")
    @Consumes(MediaType.WILDCARD)
    public Response getAll(@QueryParam("language") String language) {
        if (language == null || language.isEmpty()) {
            return Response.ok(greetingDao.findAll()).build();
        } else {
            return Response.ok(greetingDao.findByLanguage(language)).build();
        }
    }

    @GET
    @Path("/{greetingId}")
    @Consumes(MediaType.WILDCARD)
    public Response getById(@PathParam("greetingId") int greetingId) {
        try {
            return Response.ok(greetingDao.findById(greetingId)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
