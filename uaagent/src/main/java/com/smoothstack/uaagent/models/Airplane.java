package com.smoothstack.uaagent.models;

import javax.persistence.*;

@Entity
@Table(name = "airplane")
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id = 0;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id", insertable = true, updatable = true)
    AirplaneType airplaneType = new AirplaneType();

    public Airplane() {
    }

    public Airplane(Integer id, AirplaneType airplaneType) {
        this.id = id;
        this.airplaneType = airplaneType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AirplaneType getAirplaneType() {
        return airplaneType;
    }

    public void setAirplaneType(AirplaneType airplaneType) {
        this.airplaneType = airplaneType;
    }

    @Override
    public String toString() {
        return "Airplane{" +
                "id=" + id +
                ", airplaneType=" + airplaneType +
                '}';
    }
}
