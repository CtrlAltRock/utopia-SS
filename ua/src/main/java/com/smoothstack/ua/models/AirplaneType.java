package com.smoothstack.ua.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "airplane_type")
public class AirplaneType {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Getter
    @Setter
    @Column(name = "max_capacity")
    Integer max_capacity;

    public AirplaneType() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AirplaneType)) return false;
        AirplaneType that = (AirplaneType) o;
        return Objects.equals(id, that.id) && Objects.equals(max_capacity, that.max_capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, max_capacity);
    }

    @Override
    public String toString() {
        return "AirplaneType{" +
                "id=" + id +
                ", max_capacity=" + max_capacity +
                '}';
    }
}
