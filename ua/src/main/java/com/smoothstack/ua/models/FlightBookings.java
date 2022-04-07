package com.smoothstack.ua.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "flight_bookings")
public class FlightBookings implements Serializable {

    @Getter
    @Setter
    @EmbeddedId
    private FlightBookingsId flightBookingsId;

    public FlightBookings() {
    }

    public FlightBookings(FlightBookingsId flightBookingsId) {
        this.flightBookingsId = flightBookingsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlightBookings)) return false;
        FlightBookings that = (FlightBookings) o;
        return Objects.equals(flightBookingsId, that.flightBookingsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightBookingsId);
    }

    @Override
    public String toString() {
        return "FlightBookings{" +
                "flightBookingsId=" + flightBookingsId +
                '}';
    }
}
