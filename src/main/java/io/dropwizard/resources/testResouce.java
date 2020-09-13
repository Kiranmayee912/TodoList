package io.dropwizard.resources;

import java.util.List;
import io.dropwizard.api.Saying;
import com.codahale.metrics.annotation.Timed;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

import io.dropwizard.api.TodoRepresentation;
import io.dropwizard.db.Todo;
import io.dropwizard.resources.TodoService;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
public class testResouce {

    private final String template;
    private final String defaultName;
    private final AtomicLong counter;
    private final TodoService todoService;

    public testResouce(String template, String defaultName, TodoService todoService) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
        this.todoService = todoService;
    }

    /*@GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }*/

    @GET
    @Timed
    public TodoRepresentation<List<Todo>> getTodos() {
        return new TodoRepresentation<List<Todo>>(todoService.getTodos());
    }

    @GET
    @Timed
    @Path("{id}")
    public TodoRepresentation<Todo> getTodo(@PathParam("id") final int id) {
        return new TodoRepresentation<Todo>(todoService.getTodo(id));
    }

    @POST
    @Timed
    public TodoRepresentation<Todo> createTodo(@NotNull @Valid final Todo todo) {
        return new TodoRepresentation<Todo>(todoService.createTodo(todo));
    }
}