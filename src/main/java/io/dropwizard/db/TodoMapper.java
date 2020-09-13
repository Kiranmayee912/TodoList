package io.dropwizard.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import io.dropwizard.db.Todo;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import io.dropwizard.db.Todo;

public class TodoMapper implements ResultSetMapper<Todo> {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";

    public Todo map(int i, ResultSet resultSet, StatementContext statementContext)
            throws SQLException {
        return new Todo(resultSet.getInt(ID), resultSet.getString(NAME), resultSet.getString(DESCRIPTION));
    }
}