package io.dropwizard.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import  io.dropwizard.db.Todo;
import io.dropwizard.db.TodoMapper;

@RegisterMapper(TodoMapper.class)
public interface TodoDao {

    @SqlQuery("select * from todo;")
    public List<Todo> getTodos();

    @SqlQuery("select * from todo where id = :id")
    public Todo getTodo(@Bind("id") final int id);

    @SqlUpdate("insert into todo(name, description) values(:name, :description)")
    void createTodo(@BindBean final Todo todo);

    @SqlUpdate("update todo set name = :name, description = :description where id = :id")
    void editTodo(@BindBean final Todo todo);

    @SqlUpdate("delete from todo where id = :id")
    int deleteTodo(@Bind("id") final int id);

    @SqlQuery("select last_insert_id();")
    public int lastInsertId();
}