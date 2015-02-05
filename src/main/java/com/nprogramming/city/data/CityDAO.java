package com.nprogramming.city.data;

import com.nprogramming.city.domain.City;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CityDAO extends AbstractDAO<City> implements CityRepository {

    final static Logger log = LoggerFactory.getLogger(CityDAO.class);

    public CityDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<City> find(String query) {

        log.debug("Processing query: " + query);

        return list(namedQuery(
                "com.nprogramming.city.domain.City.findByName"
        ).setString("name", query + "%"));
    }
}
