/*
*  Copyright 2014 Vertafore, Inc.   All rights reserved.
*
*  Disclaimers:
*   This software is provided "as is," without warranty of any kind, express or
*   implied, including but not limited to the warranties of merchantability,
*   fitness for a particular purpose and non-infringement.  This source code
*   should not be relied upon as the sole basis for solving a problem whose
*   incorrect solution could result in injury to person or property. In no
*   event shall the author or contributors be held liable for any damages
*   arising in any way from the use of this software. The entire risk as to the
*   results and performance of this source code is assumed by the user.
*
*   Permission is granted to use this software for internal use only, subject
*   to the following restrictions:
*     1.  This source code MUST retain the above copyright notice, disclaimer,
*         and this list of conditions.
*     2.  This source code may be used ONLY within the scope of the original
*         agreement under which this source code was provided and may not be
*         distributed to any third party without the express written consent of
*         Vertafore, Inc.
*     3.  This source code along with all obligations and rights under the
*         original License Agreement may not be assigned to any third party
*         without the expressed written consent of Vertafore, Inc., except that
*         assignment may be made to a  successor to the business or
*         substantially all of its assets. All parties bind their successors,
*         executors, administrators, and assignees to all covenants of this
*         Agreement.
*
*   All advertising materials mentioning features or use of this software must
*   display the following acknowledgment:
*
*     Trademark Disclaimer:
*     All patent, copyright, trademark and other intellectual property rights
*     included in the source code are owned exclusively by Vertafore, Inc.
*/
package lab.food;

import com.google.common.base.Optional;
import lab.repository.InMemoryRepository;
import lab.repository.LongIdGenerator;
import lab.support.PATCH;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Controller
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Orders {
    private final InMemoryRepository<Long, Order> orderRepository = new InMemoryRepository<Long, Order>(new LongIdGenerator(), new DefaultOrdersInitializer());

    @GET
    @Consumes(MediaType.WILDCARD)
    public Response retrieveAll() {
        return Response.ok(orderRepository.getAll()).build();
    }

    @POST
    public Response create(@Context UriInfo uriInfo, Order.Builder orderBuilder) {
        orderBuilder.setId(null);
        Order order = orderRepository.add(orderBuilder.build());
        URI location = uriInfo.getAbsolutePathBuilder().path("{id}").build(order.getId());
        return Response.created(location).entity(order).build();
    }

    @GET
    @Path("/{id}")
    @Consumes(MediaType.WILDCARD)
    public Response retrieve(@PathParam("id") long id) {
        Optional<Order> orderResult = orderRepository.find(id);
        if (orderResult.isPresent()) {
            return Response.ok(orderResult.get()).build();
        } else {
            return Response.status(NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response createOrReplace(@PathParam("id") long id, Order.Builder orderBuilder) {
        if (id == orderBuilder.getId()) {
            return Response.ok(orderRepository.addOrReplace(orderBuilder.build())).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PATCH
    @Path("/{id}")
    public Response update(@PathParam("id") long id, Order.Builder updates) {
        if (updates.getId() != null && id != updates.getId()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Optional<Order> currentState = orderRepository.find(id);
        if (!currentState.isPresent()) {
            return Response.status(NOT_FOUND).build();
        }
        Order updatedState = Order.builder(currentState.get()).merge(updates).build();
        if (orderRepository.replace(updatedState)) {
            return Response.ok(updatedState).build();
        } else {
            // the order was deleted in between the retrieval and update
            return Response.status(NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.WILDCARD)
    public Response delete(@PathParam("id") long id) {
        return orderRepository.delete(id)
                ? Response.noContent().build()
                : Response.status(NOT_FOUND).build();
    }
}