package com.smoothstack.uaagent.models;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookingPaymentId implements Serializable {

    Integer booking_id;

    public BookingPaymentId() {
    }

    public BookingPaymentId(Integer booking_id) {
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
        if (!(o instanceof BookingPaymentId)) return false;
        BookingPaymentId that = (BookingPaymentId) o;
        return Objects.equals(booking_id, that.booking_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking_id);
    }

    @Override
    public String toString() {
        return "BookingPaymentId{" +
                "booking_id=" + booking_id +
                '}';
    }
}
