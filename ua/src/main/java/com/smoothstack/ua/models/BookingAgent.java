package com.smoothstack.ua.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "booking_agent")
public class BookingAgent {

    @Getter
    @Setter
    @EmbeddedId
    BookingAgentId bookingAgentId = new BookingAgentId();

    public BookingAgent() {
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
