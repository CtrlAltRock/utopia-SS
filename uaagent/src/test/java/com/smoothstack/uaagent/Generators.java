package com.smoothstack.uaagent;


import com.github.javafaker.Faker;
import com.smoothstack.uaagent.models.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;

public class Generators {

    Random rand = new Random();
    Faker faker = new Faker();

    public User makeUser() {
        Integer roleType = rand.nextInt(3) + 1;
        String roleName = "";
        switch (roleType) {
            case 1:
                roleName = "admin";
                break;

            case 2:
                roleName = "agent";
                break;

            case 3:
                roleName = "user";
                break;
        }
        UserRole userRole = new UserRole(roleType.longValue(), roleName);
        String fName = faker.name().firstName();
        String lName = faker.name().lastName();
        String username = fName+lName+faker.number().digit().toString();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String phone = faker.phoneNumber().cellPhone();
        return new User(null, userRole, fName, lName, username, email, password, phone);
    }

    public Passenger makePassenger(Integer idNumber) {
        Integer id = idNumber;
        Booking booking = makeBooking();
        String fName = faker.name().firstName();
        String lName = faker.name().lastName();
        Date dob = Date.valueOf(LocalDate.now().toString());
        String gender = rand.nextInt() % 2 == 0 ? "male":"female";
        String address = faker.address().fullAddress();
        return new Passenger(id, booking, fName, lName, dob, gender, address);
    }

    public Booking makeBooking() {
        Integer id = rand.nextInt(30000);
        Boolean isActive = rand.nextBoolean();
        String confirmationCode = faker.pokemon().name() + faker.number().digits(5);
        return new Booking(id, isActive, confirmationCode);
    }

    public Flight makeFlight() {
        Integer id = rand.nextInt(30000);
        return new Flight();
    }

    public String makeYoda() {
        return faker.yoda().quote();
    }




}
