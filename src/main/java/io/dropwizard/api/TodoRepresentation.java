package io.dropwizard.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class TodoRepresentation<T> {
    /*private int id;
    private String name;
    private String description;*/
    private T todo;

    public TodoRepresentation() {
    }

    public TodoRepresentation(T todo) {
        this.todo=todo;
    }

    @JsonProperty
    public T getTodo() {
        return todo;
    }


}