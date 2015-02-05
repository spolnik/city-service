package com.nprogramming.city;

import com.nprogramming.city.data.CityDAO;
import com.nprogramming.city.data.CityRepository;
import com.nprogramming.city.domain.City;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class CitySearchApplication extends Application<CitySearchConfiguration> {

    public static void main(String[] args) throws Exception {
        new CitySearchApplication().run(args);
    }

    private final HibernateBundle<CitySearchConfiguration> hibernateBundle =
            new HibernateBundle<CitySearchConfiguration>(City.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(CitySearchConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public void initialize(Bootstrap<CitySearchConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(
            CitySearchConfiguration configuration,
            Environment environment)
            throws Exception {

        configureCors(environment);

        final CityRepository repository = new CityDAO(
                hibernateBundle.getSessionFactory()
        );

        final CityResource cityResource = new CityResource(
            repository
        );

        environment.jersey().register(cityResource);
    }

    private void configureCors(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS,PATCH");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }

    @Override
    public String getName() {
        return "city-search-app";
    }
}
