package com.smoothstack.ua.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookingUserId implements Serializable {

    @Getter
    @Setter
    Integer booking_id;

    @Getter
    @Setter
    Integer user_id;

    public BookingUserId() {
    }

    public BookingUserId(Integer booking_id, Integer user_id) {
        this.booking_id = booking_id;
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
