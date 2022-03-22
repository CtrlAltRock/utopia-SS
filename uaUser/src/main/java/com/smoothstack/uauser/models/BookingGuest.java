package com.smoothstack.uauser.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "booking_guest")
public class BookingGuest {

    @EmbeddedId
    BookingGuestId booking_id;

    @Column(name = "contact_email")
    String contact_email;

    @Column(name = "contact_phone")
    String contact_phone;

    public BookingGuest() {
    }

    public BookingGuest(BookingGuestId booking_id, String contact_email, String contact_phone) {
        this.booking_id = booking_id;
        this.contact_email = contact_email;
        this.contact_phone = contact_phone;
    }

    public BookingGuestId getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(BookingGuestId booking_id) {
        this.booking_id = booking_id;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookingGuest)) return false;
        BookingGuest that = (BookingGuest) o;
        return Objects.equals(booking_id, that.booking_id) && Objects.equals(contact_email, that.contact_email) && Objects.equals(contact_phone, that.contact_phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking_id, contact_email, contact_phone);
    }

    @Override
    public String toString() {
        return "BookingGuest{" +
                "booking_id=" + booking_id +
                ", contact_email='" + contact_email + '\'' +
                ", contact_phone='" + contact_phone + '\'' +
                '}';
    }
}
