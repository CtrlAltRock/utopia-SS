package com.smoothstack.ua.models;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
public class UserRole {

    @Id
    Long id = Long.valueOf(1);

    @Column(name = "name")
    String name = "user";

    public UserRole() {
    }

    public UserRole(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
