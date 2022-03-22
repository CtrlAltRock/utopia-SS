package com.smoothstack.uauser.models;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FlightBookingsId implements Serializable {
    private Integer flight_id;
    private Integer booking_id;

    public FlightBookingsId() {
    }

    public FlightBookingsId(Integer flight_id, Integer booking_id) {
        this.flight_id = flight_id;
        this.booking_id = booking_id;
    }

    public Integer getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(Integer flight_id) {
        this.flight_id = flight_id;
    }

    public Integer getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(Integer booking_id) {
        this.booking_id = booking_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightBookingsId that = (FlightBookingsId) o;
        return Objects.equals(flight_id, that.flight_id) && Objects.equals(booking_id, that.booking_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flight_id, booking_id);
    }

    @Override
    public String toString() {
        return "FlightBookingsId{" +
                "flight_id=" + flight_id +
                ", booking_id=" + booking_id +
                '}';
    }
}
