package com.smoothstack.ua.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "booking_user")
public class BookingUser {

    @Getter
    @Setter
    @EmbeddedId
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    BookingUserId bookingUserId;


    public BookingUser() {
    }

    public BookingUser(BookingUserId bookingUserId) {
        this.bookingUserId = bookingUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookingUser)) return false;
        BookingUser that = (BookingUser) o;
        return Objects.equals(bookingUserId, that.bookingUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingUserId);
    }

    @Override
    public String toString() {
        return "BookingUser{" +
                "bookingUserId=" + bookingUserId +
                '}';
    }
}
