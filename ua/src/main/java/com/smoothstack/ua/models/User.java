package com.smoothstack.ua.models;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id = 0;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id", insertable = true, updatable = true)
    UserRole userRole = new UserRole();

    @Column(name = "given_name")
    String given_name = "Joaquin";

    @Column(name = "family_name")
    String family_name = "Pheonix";

    @Column(name = "username")
    String username = "JPtheBoss";

    @Column(name = "email")
    String email = "JP@hollywoo.com";

    @Column(name = "password")
    String password = "badPass";

    @Column(name = "phone")
    String phone = "5555555555";

    public User() {
    }

    public User(Integer id, UserRole userRole, String given_name, String family_name, String username, String email, String password, String phone) {
        this.id = id;
        this.userRole = userRole;
        this.given_name = given_name;
        this.family_name = family_name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
