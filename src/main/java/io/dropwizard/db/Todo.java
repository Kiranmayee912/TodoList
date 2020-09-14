package io.dropwizard.db;

import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "todo")
public class Todo{
    private int id;
    private String name;
    private String description;

    //@OneToMany(mappedBy = "list", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tasks> tasks = new ArrayList<Tasks>();


    public List<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<Tasks> subTasks) {
        this.tasks = subTasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Todo() {
        super();
    }

    public Todo(int id,String name,String description){
        super();
        this.id = id;
        this.name = name;
        this.description = description;
    }
}