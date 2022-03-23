package com.smoothstack.uaagent.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "booking_user")
public class BookingUser {

    @EmbeddedId
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    BookingUserId bookingUserId;


    public BookingUser() {
    }

    public BookingUser(BookingUserId bookingUserId) {
        this.bookingUserId = bookingUserId;
    }

    public BookingUserId getBookingUserId() {
        return bookingUserId;
    }

    public void setBookingUserId(BookingUserId bookingUserId) {
        this.bookingUserId = bookingUserId;
    }

    @Override
    public String toString() {
        return "BookingUser{" +
                "bookingUserId=" + bookingUserId +
                '}';
    }
}
