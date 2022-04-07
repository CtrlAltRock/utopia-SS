package com.smoothstack.ua.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "flight")
public class Flight {

    @Id
    @Getter
    @Setter
    Integer id;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "route_id", insertable = true, updatable = true)
    Route route;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "airplane_id", insertable = true, updatable = true)
    Airplane airplane;

    @Getter
    @Setter
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "departure_time")
    LocalDateTime departure_time;

    @Getter
    @Setter
    @Column(name = "reserved_seats")
    Integer reserved_seats;

    @Getter
    @Setter
    @Column(name = "seat_price")
    Float seat_price;

    public Flight() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;
        Flight flight = (Flight) o;
        return Objects.equals(id, flight.id) && Objects.equals(route, flight.route) && Objects.equals(airplane, flight.airplane) && Objects.equals(departure_time, flight.departure_time) && Objects.equals(reserved_seats, flight.reserved_seats) && Objects.equals(seat_price, flight.seat_price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, route, airplane, departure_time, reserved_seats, seat_price);
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
