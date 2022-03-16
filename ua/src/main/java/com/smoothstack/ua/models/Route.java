package com.smoothstack.ua.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "route")
public class Route implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id = 0;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "origin_id", insertable = true, updatable = true)
    Airport originAirport = new Airport();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_id", insertable = true, updatable = true)
    Airport destinationAirport = new Airport();

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Airport getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(Airport originAirport) {
        this.originAirport = originAirport;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
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
