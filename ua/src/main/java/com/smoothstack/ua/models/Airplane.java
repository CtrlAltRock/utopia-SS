package com.smoothstack.ua.models;

import javax.persistence.*;

@Entity
@Table(name = "airplane")
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id = 0;

    @ManyToOne
    @JoinColumn(name = "type_id", insertable = true, updatable = true)
    AirplaneType airplaneType = new AirplaneType();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AirplaneType getAirplaneType() {
        return airplaneType;
    }

    @Override
    public String toString() {
        return "Airplane{" +
                "id=" + id +
                ", airplaneType=" + airplaneType +
                '}';
    }
}
