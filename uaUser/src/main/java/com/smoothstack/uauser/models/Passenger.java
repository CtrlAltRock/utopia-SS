package com.smoothstack.uauser.models;
import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "passenger")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    Booking booking_id;

    @Column(name = "given_name")
    String given_name;

    @Column(name = "family_name")
    String family_name;

    @Column(name = "dob")
    Date dob;

    @Column(name = "gender")
    String gender;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Booking getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(Booking booking_id) {
        this.booking_id = booking_id;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
