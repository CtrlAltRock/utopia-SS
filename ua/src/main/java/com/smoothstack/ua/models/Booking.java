package com.smoothstack.ua.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Getter
    @Setter
    @Column(name = "is_active")
    Boolean is_active;

    @Getter
    @Setter
    @Column(name = "confirmation_code")
    String confirmation_code;

    public Booking() {
    }

    public Booking(Integer id, Boolean is_active, String confirmation_code) {
        this.id = id;
        this.is_active = is_active;
        this.confirmation_code = confirmation_code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) && Objects.equals(is_active, booking.is_active) && Objects.equals(confirmation_code, booking.confirmation_code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, is_active, confirmation_code);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", is_active=" + is_active +
                ", confirmation_code='" + confirmation_code + '\'' +
                '}';
    }
}
