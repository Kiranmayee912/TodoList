package io.dropwizard.db;

import org.hibernate.validator.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Tasks {
    private int id;
    private String name;
    private String description;
    @JsonIgnore private int todo_list_id;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "todo_list_id")
    //@JsonIgnore
    //private Todo list;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTodo_list_id() {
        //System.out.println("________________________________________________IN taskS   "+this.list );
        return todo_list_id;
    }

    public void setTodo_list_id(int id) {
        this.todo_list_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Tasks() {
        super();
    }

    public Tasks(int id, String name, String description, int todo_list_id) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.todo_list_id = todo_list_id;
//        this.list.setId(todo_list_id);
    }
}