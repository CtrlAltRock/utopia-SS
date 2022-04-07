package com.smoothstack.ua.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "route")
public class Route implements Serializable {

    @Id
    @Getter
    @Setter
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "origin_id")
    Airport originAirport;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "destination_id")
    Airport destinationAirport;

    public Route() {
    }

    public Route(Airport originAirport, Airport destinationAirport) {
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
    }

    public Route(Integer id, Airport originAirport, Airport destinationAirport) {
        this.id = id;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route = (Route) o;
        return Objects.equals(id, route.id) && Objects.equals(originAirport, route.originAirport) && Objects.equals(destinationAirport, route.destinationAirport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originAirport, destinationAirport);
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", originAirport=" + originAirport +
                ", destinationAirport=" + destinationAirport +
                '}';
    }
}
