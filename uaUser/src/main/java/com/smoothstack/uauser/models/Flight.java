package com.smoothstack.uauser.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "flight")
public class Flight {

    @Id
    Integer id = 0;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "route_id", insertable = true, updatable = true)
    Route route = new Route();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "airplane_id", insertable = true, updatable = true)
    Airplane airplane = new Airplane();

    @Column(name = "departure_time")
    LocalDateTime departure_time;

    @Column(name = "reserved_seats")
    Integer reserved_seats;

    @Column(name = "seat_price")
    Float seat_price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public LocalDateTime getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(LocalDateTime departure_time) {
        this.departure_time = departure_time;
    }

    public Integer getReserved_seats() {
        return reserved_seats;
    }

    public void setReserved_seats(Integer reserved_seats) {
        this.reserved_seats = reserved_seats;
    }

    public Float getSeat_price() {
        return seat_price;
    }

    public void setSeat_price(Float seat_price) {
        this.seat_price = seat_price;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", route=" + route +
                ", airplane=" + airplane +
                ", departure_time=" + departure_time +
                ", reserved_seats=" + reserved_seats +
                ", seat_price=" + seat_price +
                '}';
    }
}
