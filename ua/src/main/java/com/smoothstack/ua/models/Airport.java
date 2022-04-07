
package com.smoothstack.ua.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "airport")
public class Airport {

    @Id
    @Getter
    @Setter
    @Column(name = "iata_id")
    String iata_id;

    @Getter
    @Setter
    @Column(name = "city")
    String city;

    public Airport() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airport)) return false;
        Airport airport = (Airport) o;
        return Objects.equals(iata_id, airport.iata_id) && Objects.equals(city, airport.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iata_id, city);
    }

    @Override
    public String toString() {
        return "Airport{" +
                "iata_id=" + iata_id +
                ", city='" + city + '\'' +
                '}';
    }
}

