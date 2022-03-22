
package com.smoothstack.uauser.models;

import javax.persistence.*;

@Entity
@Table(name = "airport")
public class Airport {

    @Id
    @Column(name = "iata_id")
    String iata_id = "ZZZ";

    @Column(name = "city")
    String city = "Zazzoo";

    public Airport() {
    }

    public String getIata_id() {
        return iata_id;
    }

    public void setIata_id(String iata_id) {
        this.iata_id = iata_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "iata_id=" + iata_id +
                ", city='" + city + '\'' +
                '}';
    }
}

