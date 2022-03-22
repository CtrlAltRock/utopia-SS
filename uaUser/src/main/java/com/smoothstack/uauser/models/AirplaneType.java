package com.smoothstack.uauser.models;

import javax.persistence.*;

@Entity
@Table(name = "airplane_type")
public class AirplaneType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id = 0;

    @Column(name = "max_capacity")
    Integer max_capacity = 0;

    public AirplaneType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMax_capacity() {
        return max_capacity;
    }

    public void setMax_capacity(Integer max_capacity) {
        this.max_capacity = max_capacity;
    }

    @Override
    public String toString() {
        return "AirplaneType{" +
                "id=" + id +
                ", max_capacity=" + max_capacity +
                '}';
    }
}
