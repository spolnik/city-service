package com.nprogramming.city.data;

import com.nprogramming.city.domain.City;

import java.util.List;

public interface CityRepository {
    List<City> find(String query);
}
