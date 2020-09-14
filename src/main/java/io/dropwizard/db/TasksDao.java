package io.dropwizard.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;

import  io.dropwizard.db.Tasks;
import io.dropwizard.db.TasksMapper;

@RegisterMapper(TasksMapper.class)
public interface TasksDao {

    @SqlQuery("select * from tasks where todo_list_id = :todo_list_id")
    @RegisterBeanMapper(TasksListMapper.class)
    public List<Tasks> getTasks(@Bind("todo_list_id") final int todo_list_id);

    @SqlUpdate("insert into tasks(name, description,todo_list_id) values(:name, :description,:todo_list_id)")
    void createTasks(@BindBean final Tasks tasks);

    @SqlUpdate("delete from tasks where todo_list_id = :todo_list_id")
    int deleteTasks(@Bind("todo_list_id") final int todo_list_id);

}