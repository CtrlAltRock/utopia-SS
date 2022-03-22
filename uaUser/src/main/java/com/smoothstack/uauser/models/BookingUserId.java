package com.smoothstack.uauser.models;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookingUserId implements Serializable {
    Integer booking_id;
    Integer user_id;

    public BookingUserId() {
    }

    public BookingUserId(Integer booking_id, Integer user_id) {
        this.booking_id = booking_id;
        this.user_id = user_id;
    }

    public Integer getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(Integer booking_id) {
        this.booking_id = booking_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookingUserId)) return false;
        BookingUserId that = (BookingUserId) o;
        return Objects.equals(booking_id, that.booking_id) && Objects.equals(user_id, that.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking_id, user_id);
    }

    @Override
    public String toString() {
        return "BookingUserId{" +
                "booking_id=" + booking_id +
                ", user_id=" + user_id +
                '}';
    }
}
