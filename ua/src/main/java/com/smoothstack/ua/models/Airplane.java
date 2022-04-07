package com.smoothstack.ua.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "airplane")
public class Airplane {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id = 0;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "type_id", insertable = true, updatable = true)
    AirplaneType airplaneType = new AirplaneType();

    public Airplane() {
    }

    public Airplane(Integer id, AirplaneType airplaneType) {
        this.id = id;
        this.airplaneType = airplaneType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airplane)) return false;
        Airplane airplane = (Airplane) o;
        return Objects.equals(id, airplane.id) && Objects.equals(airplaneType, airplane.airplaneType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, airplaneType);
    }

    @Override
    public String toString() {
        return "Airplane{" +
                "id=" + id +
                ", airplaneType=" + airplaneType +
                '}';
    }
}
