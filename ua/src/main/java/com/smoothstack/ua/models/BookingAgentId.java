package com.smoothstack.ua.models;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookingAgentId implements Serializable {

    Integer booking_id;
    Integer agent_id;

    public BookingAgentId() {
    }

    public BookingAgentId(Integer booking_id, Integer agent_id) {
        this.booking_id = booking_id;
        this.agent_id = agent_id;
    }

    public Integer getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(Integer booking_id) {
        this.booking_id = booking_id;
    }

    public Integer getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(Integer agent_id) {
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
