package com.nprogramming.city.domain;

import com.fasterxml.jackson.annotation.JsonProperty;


import javax.persistence.*;

@Entity
@Table(name = "city")
@NamedQueries({
        @NamedQuery(
                name = "com.nprogramming.city.domain.City.findByName",
                query = "SELECT c FROM City c WHERE c.name like :name"
        )
})
public class City {

    @Id
    @Column(name = "name", nullable = false)
    private final String name;

    public City(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getName() {
        return name;
    }
}
