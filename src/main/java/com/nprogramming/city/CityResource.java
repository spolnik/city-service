package com.nprogramming.city;

import com.codahale.metrics.annotation.Timed;
import com.nprogramming.city.data.CityRepository;
import com.nprogramming.city.domain.City;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/city")
@Produces(MediaType.APPLICATION_JSON)
public class CityResource {

    private final CityRepository repository;

    public CityResource(CityRepository repository) {
        this.repository = repository;
    }

    @GET
    @Timed
    @UnitOfWork
    public City[] getCities(@QueryParam("q") String query) {

        try {
            List<City> cities = repository.find(query);
            return cities.toArray(new City[cities.size()]);
        } catch (Exception e) {
            e.printStackTrace();
            return new City[0];
        }
    }
}
