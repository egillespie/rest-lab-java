package lab.food;

import com.google.common.base.Optional;
import lab.repository.InMemoryRepository;
import lab.repository.LongIdGenerator;
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
    private final InMemoryRepository<Long, Human> humanRepository = new InMemoryRepository<Long, Human>(new LongIdGenerator());

    @GET
    public Response retrieveAll() {
        return Response.ok(humanRepository.getAll()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Context UriInfo uriInfo, Human.Builder humanBuilder) {
        humanBuilder.setId(null);
        Human human = humanRepository.add(humanBuilder.build());
        URI location = uriInfo.getAbsolutePathBuilder().path("{id}").build(human.getId());
        return Response.created(location).entity(human).build();
    }

    @GET
    @Path("/{id}")
    public Response retrieve(@PathParam("id") long id) {
        Optional<Human> human = humanRepository.find(id);
        if (human.isPresent()) {
            return Response.ok(human.get()).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response createOrReplace(@PathParam("id") long id, Human.Builder humanBuilder) {
        if (id == humanBuilder.getId()) {
            return Response.ok(humanRepository.addOrReplace(humanBuilder.build())).build();
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
        Optional<Human> currentState = humanRepository.find(id);
        if (!currentState.isPresent()) {
            return Response.status(Status.NOT_FOUND).build();
        }
        Human updatedState = Human.builder(currentState.get()).merge(updates).build();
        if (humanRepository.replace(updatedState)) {
            return Response.ok(updatedState).build();
        } else {
            // the human was deleted in between the retrieval and update
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) {
        humanRepository.delete(id);
        return Response.noContent().build();
    }
}
