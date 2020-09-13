package io.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.resources.testResouce;
import io.dropwizard.resources.TodoService;
import javax.sql.DataSource;


import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.skife.jdbi.v2.DBI;

public class todoApplication extends Application<todoConfiguration> {

    public static void main(final String[] args) throws Exception {
        new todoApplication().run(args);
    }

    @Override
    public String getName() {
        return "todo";
    }

    @Override
    public void initialize(final Bootstrap<todoConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final todoConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application

        final DataSource dataSource =
                configuration.getDataSourceFactory().build(environment.metrics(), "sql");
        DBI dbi = new DBI(dataSource);


        /*final testResouce resource = new testResouce(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        environment.jersey().register(resource);*/

        environment.jersey().register(new testResouce(configuration.getTemplate(),
                configuration.getDefaultName(),dbi.onDemand(TodoService.class)));
    }

}
