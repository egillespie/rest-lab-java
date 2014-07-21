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
package lab.meals;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import lab.repository.InMemoryRepository;
import lab.repository.LongIdGenerator;
import lab.support.PATCH;
import org.springframework.stereotype.Controller;

import javax.annotation.Nullable;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Set;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NOT_IMPLEMENTED;

@Controller
@Path("/meals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Meals {
    private final InMemoryRepository<Long, Meal> mealRepository = new InMemoryRepository<Long, Meal>(new LongIdGenerator(), new DefaultMealsInitializer());

    private static class IngredientFilter implements Predicate<Meal> {
        private final ImmutableSet<String> ingredientsToMatch;

        private IngredientFilter(Iterable<String> ingredientsToMatch) {
            this.ingredientsToMatch = ImmutableSet.copyOf(ingredientsToMatch);
        }

        @Override
        public boolean apply(@Nullable Meal meal) {
            return meal != null && !Sets.intersection(ingredientsToMatch, meal.getIngredients()).isEmpty();
        }
    }

    @GET
    @Consumes(MediaType.WILDCARD)
    public ImmutableList<Meal> retrieveAll(@QueryParam("ingredients") Set<String> ingredients, @QueryParam("sort") String sort,
                                           @QueryParam("offset") Integer offset, @QueryParam("max") Integer max) {
        if (offset != null && max != null) {
            return mealRepository.find(determineFilter(ingredients), determineSortOrder(sort), offset, max);
        } else {
            return mealRepository.find(determineFilter(ingredients), determineSortOrder(sort));
        }
    }

    private Predicate<Meal> determineFilter(Set<String> ingredients) {
        Predicate<Meal> filter = Predicates.alwaysTrue();
        if (ingredients != null && !ingredients.isEmpty()) {
            filter = new IngredientFilter(ingredients);
        }
        return filter;
    }

    private Ordering<Meal> determineSortOrder(String sort) {
        Ordering<Meal> sortOrder = Ordering.natural();
        if (sort != null) {
            if (sort.startsWith("-")) {
                String fieldName = sort.substring(1);
                if (fieldName.equalsIgnoreCase("id")) {
                    sortOrder = Ordering.natural().reverse();
                } else if (fieldName.equalsIgnoreCase("name")) {
                    sortOrder = Ordering.from(String.CASE_INSENSITIVE_ORDER).onResultOf(Meal.NAME_EXTRACTOR).reverse();
                }
            } else if (sort.equalsIgnoreCase("name")) {
                sortOrder = Ordering.from(String.CASE_INSENSITIVE_ORDER).onResultOf(Meal.NAME_EXTRACTOR);
            }
        }
        return sortOrder;
    }

    @POST
    public Response create(@Context UriInfo uriInfo, Meal.Builder mealBuilder) {
        mealBuilder.setId(null);
        Meal meal = mealRepository.add(mealBuilder.build());
        URI location = uriInfo.getAbsolutePathBuilder().path("{id}").build(meal.getId());
        return Response.created(location).entity(meal).build();
    }

    @GET
    @Path("/{id}")
    @Consumes(MediaType.WILDCARD)
    public Response retrieve(@PathParam("id") long id) {
        Optional<Meal> mealResult = mealRepository.find(id);
        if (mealResult.isPresent()) {
            return Response.ok(mealResult.get()).build();
        } else {
            return Response.status(NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response createOrReplace(@PathParam("id") long id, Meal.Builder mealBuilder) {
        if (id == mealBuilder.getId()) {
            return Response.ok(mealRepository.addOrReplace(mealBuilder.build())).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PATCH
    @Path("/{id}")
    public Response update(@PathParam("id") long id, Meal.Builder updates) {
        if (updates.getId() != null && id != updates.getId()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Optional<Meal> currentState = mealRepository.find(id);
        if (!currentState.isPresent()) {
            return Response.status(NOT_FOUND).build();
        }
        Meal updatedState = Meal.builder(currentState.get()).merge(updates).build();
        if (mealRepository.replace(updatedState)) {
            return Response.ok(updatedState).build();
        } else {
            // the meal was deleted in between the retrieval and update
            return Response.status(NOT_FOUND).build();
        }
    }

    /**
     * @return possible responses:
     * <ul>
     *     <li>204 - Entity was successfully deleted</li>
     *     <li>404 - Entity was not found</li>
     * </ul>
     */
    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.WILDCARD)
    public Response delete(@PathParam("id") long id) {
        return mealRepository.delete(id)
                ? Response.noContent().build()
                : Response.status(NOT_FOUND).build();
    }

    /**
     * @return 503 - Resource is not yet implemented
     */
    @GET
    @Path("/{mealId}/ingredients")
    @Consumes(MediaType.WILDCARD)
    public Response getMealIngredients(@PathParam("mealId") long id) {
        return Response.status(NOT_IMPLEMENTED).build();
    }
}
