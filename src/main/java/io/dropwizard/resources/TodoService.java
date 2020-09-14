package io.dropwizard.resources;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import com.google.common.base.Preconditions;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import io.dropwizard.db.TodoDao;
import io.dropwizard.db.Todo;
import io.dropwizard.db.TasksDao;
import io.dropwizard.db.Tasks;

public abstract class TodoService {
    private static final String TODO_ID_IS_NOT_FOUND = "Todo id %s not found.";
    private static final String TODO_NAME_CANNOT_BE_NULL = "Todo name should not be empty.";
    private static final String TASK_NAME_CANNOT_BE_NULL = "Task name should not be empty.";
    private static final String SUCCESS = "Successfully deleted...";
    private static final String UNEXPECTED_ERROR = "An unexpected error occurred while deleting todo.";

    @CreateSqlObject
    abstract TodoDao todoDao();

    @CreateSqlObject
    abstract TasksDao tasksDao();

    public List<Todo> getTodos() {
        List<Todo> todoList = todoDao().getTodos();
        for(Todo todo:todoList)
        {
            int id = todo.getId();
            List<Tasks> tasksList = tasksDao().getTasks(id);
            for (Tasks task : tasksList)
                task.setTodo_list_id(id);
            todo.setTasks(tasksList);
        }
        return todoList;
    }

    public Todo getTodo(int id) {
        Todo todo = todoDao().getTodo(id);
        List<Tasks> tasksList= tasksDao().getTasks(id);
        for(Tasks task:tasksList)
            task.setTodo_list_id(id);
        todo.setTasks(tasksList);
        if (Objects.isNull(todo)) {
            throw new WebApplicationException(String.format(TODO_ID_IS_NOT_FOUND, id), Status.NOT_FOUND);
        }
        return todo;
    }

    public Todo createTodo(Todo todo) {
        if (Objects.isNull(todo.getName())) {
            throw new WebApplicationException(TODO_NAME_CANNOT_BE_NULL, Status.PRECONDITION_FAILED);
        }
        todoDao().createTodo(todo);
        int id = todoDao().lastInsertId();
        todo.setId(id);

        if (!todo.getTasks().isEmpty()) {
            List<Tasks> tasksList = todo.getTasks();
            for (Tasks t : tasksList) {
                if (Objects.isNull(t.getName())) {
                    throw new WebApplicationException(TASK_NAME_CANNOT_BE_NULL, Status.PRECONDITION_FAILED);
                }
                t.setTodo_list_id(id);
                tasksDao().createTasks(t);
            }
        }
        return getTodo(id);
    }


    public Todo editTodo(Todo todo) {
        if (Objects.isNull(todoDao().getTodo(todo.getId()))) {
            throw new WebApplicationException(String.format(TODO_ID_IS_NOT_FOUND, todo.getId()),
                    Status.NOT_FOUND);
        }
        if (Objects.isNull(todo.getName())) {
            throw new WebApplicationException(TODO_NAME_CANNOT_BE_NULL, Status.PRECONDITION_FAILED);
        }
        todoDao().editTodo(todo);
        return getTodo(todo.getId());
    }

    public String deleteTodo(final int id) {
        int result = todoDao().deleteTodo(id);
        tasksDao().deleteTasks(id);
        switch (result) {
            case 1:
                return SUCCESS;
            case 0:
                throw new WebApplicationException(String.format(TODO_ID_IS_NOT_FOUND, id), Status.NOT_FOUND);
            default:
                throw new WebApplicationException(UNEXPECTED_ERROR, Status.INTERNAL_SERVER_ERROR);
        }
    }


}