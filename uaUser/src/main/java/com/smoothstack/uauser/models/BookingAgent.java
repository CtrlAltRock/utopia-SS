package com.smoothstack.uauser.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "booking_agent")
public class BookingAgent {

    @EmbeddedId
    BookingAgentId bookingAgentId = new BookingAgentId();

    public BookingAgent() {
    }

    public BookingAgent(BookingAgentId bookingAgentId) {
        this.bookingAgentId = bookingAgentId;
    }

    public BookingAgentId getBookingAgentId() {
        return bookingAgentId;
    }

    public void setBookingAgentId(BookingAgentId bookingAgentId) {
        this.bookingAgentId = bookingAgentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookingAgent)) return false;
        BookingAgent that = (BookingAgent) o;
        return Objects.equals(bookingAgentId, that.bookingAgentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingAgentId);
    }

    @Override
    public String toString() {
        return "BookingAgent{" +
                "bookingAgentId=" + bookingAgentId +
                '}';
    }
}
