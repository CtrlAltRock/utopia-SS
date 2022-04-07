package com.smoothstack.ua.models;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "passenger")
public class Passenger {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    Booking booking_id;

    @Getter
    @Setter
    @Column(name = "given_name")
    String given_name;

    @Getter
    @Setter
    @Column(name = "family_name")
    String family_name;

    @Getter
    @Setter
    @Column(name = "dob")
    Date dob;

    @Getter
    @Setter
    @Column(name = "gender")
    String gender;

    @Getter
    @Setter
    @Column(name = "address")
    String address;

    public Passenger() {
    }

    public Passenger(Integer id, Booking booking_id, String given_name, String family_name, Date dob, String gender, String address) {
        this.id = id;
        this.booking_id = booking_id;
        this.given_name = given_name;
        this.family_name = family_name;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger)) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(id, passenger.id) && Objects.equals(booking_id, passenger.booking_id) && Objects.equals(given_name, passenger.given_name) && Objects.equals(family_name, passenger.family_name) && Objects.equals(dob, passenger.dob) && Objects.equals(gender, passenger.gender) && Objects.equals(address, passenger.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, booking_id, given_name, family_name, dob, gender, address);
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "booking_id=" + booking_id +
                ", given_name='" + given_name + '\'' +
                ", family_name='" + family_name + '\'' +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
