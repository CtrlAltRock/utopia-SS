package com.smoothstack.ua.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "flight_bookings")
public class FlightBookings implements Serializable {

    @EmbeddedId
    private FlightBookingsId flightBookingsId;

    public FlightBookings() {
    }

    public FlightBookings(FlightBookingsId flightBookingsId) {
        this.flightBookingsId = flightBookingsId;
    }

    public FlightBookingsId getFlightBookingsId() {
        return flightBookingsId;
    }

    public void setFlightBookingsId(FlightBookingsId flightBookingsId) {
        this.flightBookingsId = flightBookingsId;
    }

    @Override
    public String toString() {
        return "FlightBookings{" +
                "flightBookingsId=" + flightBookingsId +
                '}';
    }
}
