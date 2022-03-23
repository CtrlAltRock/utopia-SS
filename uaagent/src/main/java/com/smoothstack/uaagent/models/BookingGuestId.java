package com.smoothstack.uaagent.models;

import java.io.Serializable;
import java.util.Objects;

public class BookingGuestId implements Serializable {

    Integer booking_id;

    public BookingGuestId() {
    }

    public BookingGuestId(Integer booking_id) {
        this.booking_id = booking_id;
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
        if (!(o instanceof BookingGuestId)) return false;
        BookingGuestId that = (BookingGuestId) o;
        return Objects.equals(booking_id, that.booking_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking_id);
    }

    @Override
    public String toString() {
        return "BookingGuestId{" +
                "booking_id=" + booking_id +
                '}';
    }
}
