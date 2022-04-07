package com.smoothstack.ua.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "role_id")
    UserRole userRole;

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
    @Column(name = "username")
    String username;

    @Getter
    @Setter
    @Column(name = "email")
    String email;

    @Getter
    @Setter
    @Column(name = "password")
    String password;

    @Getter
    @Setter
    @Column(name = "phone")
    String phone;

    public User() {
    }

    public User(Long id, UserRole userRole, String given_name, String family_name, String username, String email, String password, String phone) {
        this.id = id;
        this.userRole = userRole;
        this.given_name = given_name;
        this.family_name = family_name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(userRole, user.userRole) && Objects.equals(given_name, user.given_name) && Objects.equals(family_name, user.family_name) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userRole, given_name, family_name, username, email, password, phone);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role_id=" + userRole +
                ", given_name='" + given_name + '\'' +
                ", family_name='" + family_name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
