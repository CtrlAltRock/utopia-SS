package com.smoothstack.ua;

import com.github.javafaker.Faker;
import com.smoothstack.ua.models.*;
import com.smoothstack.ua.services.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {UaApplication.class})
public class AdminCrudTests {

    Logger logger = LoggerFactory.getLogger(AdminCrudTests.class);
    @Autowired
    AdminService adminService;


    Airplane airplane;
    AirplaneType airplaneType;
    Airport origin_airport;
    Airport destination_airport;
    Booking booking;
    BookingAgent bookingAgent;
    BookingAgentId bookingAgentId;
    BookingGuest bookingGuest;
    BookingGuestId bookingGuestId;
    BookingPayment bookingPayment;
    BookingPaymentId bookingPaymentId;
    BookingUser bookingUser;
    BookingUserId bookingUserId;
    Flight flight;
    FlightBookings flightBookings;
    FlightBookingsId flightBookingsId;
    Passenger passenger;
    Route route;
    User user;
    UserRole userRole;

    @BeforeEach
    public void setUp() {
        airplaneType = new AirplaneType();
        airplaneType.setId(1);
        airplaneType.setMax_capacity(42);

        airplane = new Airplane();
        airplane.setId(1);
        airplane.setAirplaneType(airplaneType);

        origin_airport = new Airport();
        origin_airport.setIata_id("GNV");
        origin_airport.setCity("Gainesville");

        destination_airport = new Airport();
        destination_airport.setIata_id("BWI");
        destination_airport.setCity("Baltimore");

        booking = new Booking();
        booking.setId(1);
        booking.setIs_active(true);
        booking.setConfirmation_code("SMOOTHSTACK");

        bookingAgentId = new BookingAgentId();
        bookingAgent = new BookingAgent();
        bookingAgentId.setBooking_id(1);
        bookingAgentId.setBooking_id(1);
        bookingAgent.setBookingAgentId(bookingAgentId);

        bookingGuestId = new BookingGuestId();
        bookingGuest = new BookingGuest();
        bookingGuestId.setBooking_id(1);
        bookingGuest.setBooking_id(bookingGuestId);
        bookingGuest.setContact_email("samuel.sessums@smoothstack.com");
        bookingGuest.setContact_phone("3522623301");

        bookingPaymentId = new BookingPaymentId();
        bookingPayment = new BookingPayment();
        bookingPaymentId.setBooking_id(1);
        bookingPayment.setBookingPaymentId(bookingPaymentId);
        bookingPayment.setStripe_id("928374198734918743");
        bookingPayment.setRefunded(false);

        userRole = new UserRole();
        user = new User();
        userRole.setId(1);
        userRole.setName("user");
        user.setId(1);
        user.setUserRole(userRole);
        user.setGiven_name("Sam");
        user.setFamily_name("Sessums");
        user.setUsername("sbsessums");
        user.setEmail("samuel.sessums@smoothstack.com");
        user.setPassword("unencryptedpass");
        user.setPhone("3522623301");

        bookingUserId = new BookingUserId();
        bookingUser = new BookingUser();
        bookingUserId.setBooking_id(1);
        bookingUserId.setUser_id(1);
        bookingUser.setBookingUserId(bookingUserId);

        route = new Route();
        route.setId(1);
        route.setOriginAirport(origin_airport);
        route.setDestinationAirport(destination_airport);

        flight = new Flight();
        flight.setId(1);
        flight.setRoute(route);
        flight.setAirplane(airplane);
        flight.setDeparture_time(LocalDateTime.now());
        flight.setReserved_seats(42);
        flight.setSeat_price(50f);

        flightBookingsId = new FlightBookingsId();
        flightBookings = new FlightBookings();
        flightBookingsId.setBooking_id(1);
        flightBookingsId.setFlight_id(1);
        flightBookings.setFlightBookingsId(flightBookingsId);

        passenger = new Passenger();
        passenger.setId(1);
        passenger.setBooking_id(booking);
        passenger.setGiven_name(user.getGiven_name());
        passenger.setFamily_name(user.getFamily_name());
        passenger.setDob(Date.valueOf(LocalDate.now().toString()));
        passenger.setGender("male");
        passenger.setAddress("5248 NW 48th Avenue");

    }


    @Test
    @Order(1)
    public void createAirplaneType() {
        adminService.saveAirplaneType(airplaneType);
        Assertions.assertTrue(adminService.getALLAirplaneTypes().size() == 1);
    }


    @Test
    @BeforeTestMethod("createAirplaneType")
    @Order(2)
    public void readAirplaneType() {
        AirplaneType airplaneType = adminService.getALLAirplaneTypes().get(0);
        Assertions.assertTrue(airplaneType.getId() == 1);

    }



    @Test
    @BeforeTestMethod("createAirplaneType")
    @Order(3)
    public void updateAirplaneType() {
        AirplaneType airplaneType = adminService.getALLAirplaneTypes().get(0);
        Assertions.assertTrue(airplaneType.getId() == 1);
        airplaneType.setMax_capacity(23);
        adminService.saveAirplaneType(airplaneType);
        airplaneType = adminService.getALLAirplaneTypes().get(0);
        Assertions.assertTrue(airplaneType.getMax_capacity() == 23);
    }


    @Test
    @BeforeTestMethod("createAirplaneType")
    @Order(4)
    public void deleteAirplaneType() {
        AirplaneType airplaneType = adminService.getALLAirplaneTypes().get(0);
        adminService.deleteAirplaneType(airplaneType);
        Assertions.assertTrue(adminService.getALLAirplaneTypes().size() == 0);
    }


    @Test
    @Order(5)
    public void createAirplane() {
        adminService.saveAirplane(airplane);
        Assertions.assertTrue(adminService.getAllAirplanes().size() == 1);
    }

    @Test
    @BeforeTestMethod("createAirplane")
    @Order(6)
    public void readAirplane() {
        Assertions.assertTrue(adminService.getAllAirplanes().size() == 1);
        Airplane airplane = adminService.getAllAirplanes().get(0);
        logger.info(airplane.toString());
        Assertions.assertTrue(airplane.getAirplaneType().equals(airplaneType));
        Assertions.assertTrue(airplane.getId() == 1);
    }

    @Test
    @Order(7)
    public void updateAirplane() {

    }

    @Test
    @Order(8)
    public void deleteAirplane() {

    }

    @Test
    @Order(9)
    public void createAirport() {

    }

    @Test
    @Order(10)
    public void readAirport() {

    }

    @Test
    @Order(11)
    public void updateAirport() {

    }

    @Test
    @Order(12)
    public void deleteAirport() {

    }

    @Test
    @Order(13)
    public void createBooking() {

    }

    @Test
    @Order(14)
    public void readBooking() {

    }

    @Test
    @Order(15)
    public void updateBooking() {

    }

    @Test
    @Order(16)
    public void deleteBooking() {

    }

    @Test
    @Order(17)
    public void createBookingAgent() {

    }

    @Test
    @Order(18)
    public void readBookingAgent() {

    }

    @Test
    @Order(19)
    public void updateBookingAgent() {

    }

    @Test
    @Order(20)
    public void deleteBookingAgent() {

    }

    @Test
    @Order(21)
    public void createBookingGuest() {

    }

    @Test
    @Order(22)
    public void readBookingGuest() {

    }

    @Test
    @Order(23)
    public void updateBookingGuest() {

    }

    @Test
    @Order(24)
    public void deleteBookingGuest() {

    }

    @Test
    @Order(25)
    public void createBookingPayment() {

    }

    @Test
    @Order(26)
    public void readBookingPayment() {

    }

    @Test
    @Order(27)
    public void updateBookingPayment() {

    }

    @Test
    @Order(28)
    public void deleteBookingPayment() {

    }

    @Test
    @Order(29)
    public void createBookingUser() {

    }

    @Test
    @Order(30)
    public void readBookingUser() {

    }


    @Test
    @Order(31)
    public void updateBookingUser() {

    }

    @Test
    @Order(32)
    public void deleteBookingUser() {

    }


    @Test
    @Order(33)
    public void createFlight() {
        AirplaneType airplaneType = new AirplaneType();
        airplaneType.setMax_capacity(150);

        Airplane airplane = new Airplane();
        airplane.setAirplaneType(airplaneType);


        Route route = new Route();

        Airport o_airport = new Airport();
        o_airport.setIata_id("BAD");
        o_airport.setCity("sdkflj");
        Airport d_airport = new Airport();
        d_airport.setIata_id("GLD");
        d_airport.setCity("ksjfdlk");
        route.setOriginAirport(o_airport);
        route.setDestinationAirport(d_airport);

        Integer route_id = 1;
        Integer airplane_id = 6;
        LocalDateTime depart_time = LocalDateTime.now();
        Integer reservedSeats = 150;
        Float seatPrice = 75.34f;

        Flight flight = new Flight();
        flight.setRoute(route);
        flight.setAirplane(airplane);
        flight.setDeparture_time(depart_time);
        flight.setReserved_seats(reservedSeats);
        flight.setSeat_price(seatPrice);

        adminService.saveFlight(flight);
        List<Flight> flights = adminService.getFlights();
        //System.out.println(flights.get(0));
        assert(flights.size() == 1);
    }

    @Test
    @Order(34)
    public void readFlight() {

    }

    @Test
    @Order(35)
    public void updateFlight() {

    }

    @Test
    @Order(36)
    public void deleteFlight() {

    }

    @Test
    @Order(37)
    public void createFlightBookings() {

    }

    @Test
    @Order(38)
    public void readFlightBookings() {

    }

    @Test
    @Order(40)
    public void updateFlightBookings() {

    }

    @Test
    @Order(41)
    public void deleteFlightBookings() {

    }

    @Test
    @Order(42)
    public void createPassenger() {

    }

    @Test
    @Order(43)
    public void readPassenger() {

    }

    @Test
    @Order(44)
    public void updatePassenger() {

    }

    @Test
    @Order(45)
    public void deletePassenger() {

    }

    @Test
    @Order(46)
    public void createRoute() {

    }

    @Test
    @Order(47)
    public void readRoute() {

    }

    @Test
    @Order(48)
    public void updateRoute() {

    }

    @Test
    @Order(49)
    public void deleteRoute() {

    }

    @Test
    @Order(50)
    public void createUser() {

    }

    @Test
    @Order(51)
    public void readUser() {

    }

    @Test
    @Order(52)
    public void updateUser() {

    }

    @Test
    @Order(53)
    public void deleteUser() {

    }

    @Test
    @Order(54)
    public void createUserRole() {

    }

    @Test
    @Order(55)
    public void readUserRole() {

    }

    @Test
    @Order(56)
    public void updateUserRole() {

    }

    @Test
    @Order(57)
    public void deleteUserRole() {

    }


}
