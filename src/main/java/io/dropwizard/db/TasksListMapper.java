package io.dropwizard.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import io.dropwizard.db.Tasks;

public class TasksListMapper implements ResultSetMapper<List<Tasks>> {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String TODO_LIST_ID = "todo_list_id";

    public List<Tasks> map(int i, ResultSet resultSet, StatementContext statementContext)
            throws SQLException {
        List<Tasks> tasksList=new ArrayList<Tasks>();
        while( resultSet.next()) {
            Tasks task = new Tasks(resultSet.getInt(ID), resultSet.getString(NAME), resultSet.getString(DESCRIPTION),resultSet.getInt(TODO_LIST_ID));
            tasksList.add(task);
            System.out.println("LIst Mapper: "+resultSet.getString(NAME));
        }
        // return new Tasks(resultSet.getInt(ID), resultSet.getString(NAME), resultSet.getString(DESCRIPTION),resultSet.getInt(TODO_LIST_ID));
        return tasksList;
    }
}