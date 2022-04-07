package com.smoothstack.ua.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookingAgentId implements Serializable {

    @Getter
    @Setter
    Integer booking_id;

    @Getter
    @Setter
    Integer agent_id;

    public BookingAgentId() {
    }

    public BookingAgentId(Integer booking_id, Integer agent_id) {
        this.booking_id = booking_id;
        this.agent_id = agent_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookingAgentId)) return false;
        BookingAgentId that = (BookingAgentId) o;
        return booking_id.equals(that.booking_id) && agent_id.equals(that.agent_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking_id, agent_id);
    }

    @Override
    public String toString() {
        return "BookingAgentId{" +
                "booking_id=" + booking_id +
                ", agent_id=" + agent_id +
                '}';
    }
}
