package io.dropwizard.resources;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import com.google.common.base.Preconditions;

import io.dropwizard.db.TodoDao;
import io.dropwizard.db.Todo;

public abstract class TodoService {
    private static final String TODO_ID_IS_NOT_FOUND = "Todo id %s not found.";
    private static final String TODO_NAME_CANNOT_BE_NULL = "Todo name should be empty.";
    private static final String DATABASE_REACH_ERROR =
            "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
    private static final String DATABASE_CONNECTION_ERROR =
            "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
    private static final String DATABASE_UNEXPECTED_ERROR =
            "Unexpected error occurred while attempting to reach the database. Details: ";
    private static final String SUCCESS = "Success...";
    private static final String UNEXPECTED_ERROR = "An unexpected error occurred while deleting todo.";

    @CreateSqlObject
    abstract TodoDao todoDao();

    public List<Todo> getTodos() {
        return todoDao().getTodos();
    }

    public Todo getTodo(int id) {
        Todo todo = todoDao().getTodo(id);
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
        return todoDao().getTodo(todoDao().lastInsertId());
    }


    public Todo editTodo(Todo todo) {
        if (Objects.isNull(todoDao().getTodo(todo.getId()))) {
            throw new WebApplicationException(String.format(TODO_ID_IS_NOT_FOUND, todo.getId()),
                    Status.NOT_FOUND);
        }
        todoDao().editTodo(todo);
        return todoDao().getTodo(todo.getId());
    }

    public String deleteTodo(final int id) {
        int result = todoDao().deleteTodo(id);
        switch (result) {
            case 1:
                return SUCCESS;
            case 0:
                throw new WebApplicationException(String.format(TODO_ID_IS_NOT_FOUND, id), Status.NOT_FOUND);
            default:
                throw new WebApplicationException(UNEXPECTED_ERROR, Status.INTERNAL_SERVER_ERROR);
        }
    }
    /*
    public String performHealthCheck() {
        try {
            todosDao().getTodos();
        } catch (UnableToObtainConnectionException ex) {
            return checkUnableToObtainConnectionException(ex);
        } catch (UnableToExecuteStatementException ex) {
            return checkUnableToExecuteStatementException(ex);
        } catch (Exception ex) {
            return DATABASE_UNEXPECTED_ERROR + ex.getCause().getLocalizedMessage();
        }
        return null;
    }

    private String checkUnableToObtainConnectionException(UnableToObtainConnectionException ex) {
        if (ex.getCause() instanceof java.sql.SQLNonTransientConnectionException) {
            return DATABASE_REACH_ERROR + ex.getCause().getLocalizedMessage();
        } else if (ex.getCause() instanceof java.sql.SQLException) {
            return DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
        } else {
            return DATABASE_UNEXPECTED_ERROR + ex.getCause().getLocalizedMessage();
        }
    }

    private String checkUnableToExecuteStatementException(UnableToExecuteStatementException ex) {
        if (ex.getCause() instanceof java.sql.SQLSyntaxErrorException) {
            return DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
        } else {
            return DATABASE_UNEXPECTED_ERROR + ex.getCause().getLocalizedMessage();
        }
    }*/
}