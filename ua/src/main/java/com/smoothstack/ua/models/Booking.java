package com.smoothstack.ua.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "is_active")
    Boolean is_active;

    @Column(name = "confirmation_code")
    String confirmation_code;

    public Booking() {
    }

    public Booking(Integer id, Boolean is_active, String confirmation_code) {
        this.id = id;
        this.is_active = is_active;
        this.confirmation_code = confirmation_code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public String getConfirmation_code() {
        return confirmation_code;
    }

    public void setConfirmation_code(String confirmation_code) {
        this.confirmation_code = confirmation_code;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", is_active=" + is_active +
                ", confirmation_code='" + confirmation_code + '\'' +
                '}';
    }

    //    @OneToOne
//    BookingPayment bookingPayment;
//
//    @OneToOne
//    BookingAgent bookingAgent;
//
//    @OneToOne
//    BookingUser bookingUser;
//
//    @OneToOne
//    BookingGuest bookingGuest;
//
//    @OneToMany
//    Set<FlightBookings> flightBookings;
//
//    public Booking() {
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Boolean getIs_active() {
//        return is_active;
//    }
//
//    public void setIs_active(Boolean is_active) {
//        this.is_active = is_active;
//    }
//
//    public String getConfirmation_code() {
//        return confirmation_code;
//    }
//
//    public void setConfirmation_code(String confirmation_code) {
//        this.confirmation_code = confirmation_code;
//    }
//
//    public BookingPayment getBookingPayment() {
//        return bookingPayment;
//    }
//
//    public void setBookingPayment(BookingPayment bookingPayment) {
//        this.bookingPayment = bookingPayment;
//    }
//
//    public BookingAgent getBookingAgent() {
//        return bookingAgent;
//    }
//
//    public void setBookingAgent(BookingAgent bookingAgent) {
//        this.bookingAgent = bookingAgent;
//    }
//
//    public BookingUser getBookingUser() {
//        return bookingUser;
//    }
//
//    public void setBookingUser(BookingUser bookingUser) {
//        this.bookingUser = bookingUser;
//    }
//
//    public BookingGuest getBookingGuest() {
//        return bookingGuest;
//    }
//
//    public void setBookingGuest(BookingGuest bookingGuest) {
//        this.bookingGuest = bookingGuest;
//    }
//
//    public Set<FlightBookings> getFlightBookings() {
//        return flightBookings;
//    }
//
//    public void setFlightBookings(Set<FlightBookings> flightBookings) {
//        this.flightBookings = flightBookings;
//    }
//
//    @Override
//    public String toString() {
//        return "Booking{" +
//                "id=" + id +
//                ", is_active=" + is_active +
//                ", confirmation_code='" + confirmation_code + '\'' +
//                ", bookingPayment=" + bookingPayment +
//                ", bookingAgent=" + bookingAgent +
//                ", bookingUser=" + bookingUser +
//                ", bookingGuest=" + bookingGuest +
//                ", flightBookings=" + flightBookings +
//                '}';
//    }
}
