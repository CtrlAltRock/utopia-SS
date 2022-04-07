package com.smoothstack.ua.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "booking_payment")
public class BookingPayment implements Serializable {

    @Getter
    @Setter
    @EmbeddedId
    BookingPaymentId bookingPaymentId;

    @Getter
    @Setter
    @Column(name = "stripe_id")
    String stripe_id;

    @Getter
    @Setter
    @Column(name = "refunded")
    Boolean refunded;

    public BookingPayment() {
    }


    public BookingPayment(BookingPaymentId bookingPaymentId, String stripe_id, Boolean refunded) {
        this.bookingPaymentId = bookingPaymentId;
        this.stripe_id = stripe_id;
        this.refunded = refunded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookingPayment)) return false;
        BookingPayment that = (BookingPayment) o;
        return Objects.equals(bookingPaymentId, that.bookingPaymentId) && Objects.equals(stripe_id, that.stripe_id) && Objects.equals(refunded, that.refunded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingPaymentId, stripe_id, refunded);
    }

    @Override
    public String toString() {
        return "BookingPayment{" +
                "bookingPaymentId=" + bookingPaymentId +
                ", stripe_id='" + stripe_id + '\'' +
                ", refunded=" + refunded +
                '}';
    }
}
