package com.smoothstack.ua;

import com.smoothstack.ua.models.*;
import com.smoothstack.ua.services.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@DirtiesContext
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
        bookingAgentId.setAgent_id(1);
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
        userRole.setId(1L);
        userRole.setName("user");
        user.setId(1L);
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
        Airplane new_airplane = adminService.getAllAirplanes().get(0);
        logger.info(new_airplane.toString());
        logger.info(adminService.getALLAirplaneTypes().toString());
        //Assertions.assertTrue(airplane.getAirplaneType().equals(airplaneType));
        Assertions.assertTrue(new_airplane.getId() == 1);
    }

    @Test
    @BeforeTestMethod("readAirplane")
    @Order(7)
    public void updateAirplane() {
        Airplane new_airplane = adminService.getAllAirplanes().get(0);
        new_airplane.setAirplaneType(airplaneType);
        adminService.saveAirplane(new_airplane);
        Assertions.assertTrue(adminService.getAllAirplanes().size() == 1);
        Assertions.assertTrue(adminService.getAllAirplanes().get(0).getId() == 1);

    }

    @Test
    @BeforeTestMethod("updateAirplane")
    @Order(8)
    public void deleteAirplane() {
        adminService.deleteAirplane(adminService.getAirplaneById(1));
        Assertions.assertTrue(adminService.getAllAirplanes().size() == 0);
    }

    @Test
    @Order(9)
    public void createAirport() {
        adminService.saveAirport(origin_airport);
        Assertions.assertTrue(adminService.getAllAirports().size() == 1);
    }

    @Test
    @BeforeTestMethod("createAirport")
    @Order(10)
    public void readAirport() {
        Assertions.assertTrue(adminService.getAllAirports().size() == 1);
        Airport airport = adminService.getAllAirports().get(0);
    }

    @Test
    @BeforeTestMethod("createAirport")
    @Order(11)
    public void updateAirport() {
        Airport airport = adminService.getAllAirports().get(0);
        airport.setCity("new city");
        adminService.saveAirport(airport);
        Assertions.assertTrue(adminService.getAllAirports().size() == 1);
    }

    @Test
    @BeforeTestMethod("updateAirport")
    @Order(12)
    public void deleteAirport() {
        Assertions.assertTrue(adminService.getAllAirports().size() == 1);
        adminService.deleteAirport(adminService.getAllAirports().get(0));
        Assertions.assertTrue(adminService.getAllAirports().size() == 0);
    }

    @Test
    @Order(13)
    public void createBooking() {
        Assertions.assertTrue(adminService.getAllBookings().size() == 0);
        adminService.saveBooking(booking);
        Assertions.assertTrue(adminService.getAllBookings().size() == 1);

    }

    @Test
    @BeforeTestMethod("createBooking")
    @Order(14)
    public void readBooking() {
        Assertions.assertTrue(adminService.getAllBookings().size() == 1);
        Booking new_booking = adminService.getAllBookings().get(0);
        Assertions.assertTrue(new_booking.getIs_active() == true);

    }

    @Test
    @BeforeTestMethod("createBooking")
    @Order(15)
    public void updateBooking() {
        Assertions.assertTrue(adminService.getAllBookings().size() == 1);
        Booking new_booking = adminService.getAllBookings().get(0);
        new_booking.setIs_active(false);
        adminService.saveBooking(new_booking);
        Assertions.assertTrue(adminService.getAllBookings().size() == 1);
        Assertions.assertTrue(adminService.getAllBookings().get(0).getIs_active() == false);
    }

    @Test
    @BeforeTestMethod("updateBooking")
    @Order(16)
    public void deleteBooking() {
        Assertions.assertTrue(adminService.getAllBookings().size() == 1);
        adminService.deleteBooking(adminService.getAllBookings().get(0));
        Assertions.assertTrue(adminService.getAllBookings().size() == 0);

    }

    @Test
    @Order(17)
    public void createBookingAgent() {
        Assertions.assertTrue(adminService.getALlBookingAgents().size() == 0);
        adminService.saveBookingAgent(bookingAgent);
        Assertions.assertTrue(adminService.getALlBookingAgents().size() == 1);
    }

    @Test
    @BeforeTestMethod("createBookingAgent")
    @Order(18)
    public void readBookingAgent() {
        Assertions.assertTrue(adminService.getALlBookingAgents().size() == 1);
        BookingAgent new_bookingAgent = adminService.getALlBookingAgents().get(0);
        logger.info(new_bookingAgent.toString());
        Assertions.assertTrue(new_bookingAgent.getBookingAgentId().getAgent_id() == bookingAgentId.getAgent_id());

    }

    @Test
    @BeforeTestMethod("createBookingAgent")
    @Order(19)
    public void updateBookingAgent() {
        Assertions.assertTrue(adminService.getALlBookingAgents().size() == 1);
        BookingAgent new_bookingAgent = adminService.getALlBookingAgents().get(0);
        new_bookingAgent.getBookingAgentId().setBooking_id(1);
        adminService.saveBookingAgent(new_bookingAgent);
        Assertions.assertTrue(adminService.getALlBookingAgents().size() == 1);

    }

    @Test
    @BeforeTestMethod("createBookingAgent")
    @Order(20)
    public void deleteBookingAgent() {
        Assertions.assertTrue(adminService.getALlBookingAgents().size() == 1);
        adminService.deleteBookingAgent(adminService.getALlBookingAgents().get(0));
        Assertions.assertTrue(adminService.getALlBookingAgents().size() == 0);


    }

    @Test
    @Order(21)
    public void createBookingGuest() {
        Assertions.assertTrue(adminService.getAllBookingGuests().size() == 0);
        adminService.saveBookingGuest(bookingGuest);
        Assertions.assertTrue(adminService.getAllBookingGuests().size() == 1);

    }

    @Test
    @BeforeTestMethod("createBookingGuest")
    @Order(22)
    public void readBookingGuest() {
        Assertions.assertTrue(adminService.getAllBookingGuests().size() == 1);
        BookingGuest new_bookingGuest = adminService.getAllBookingGuests().get(0);
        Assertions.assertTrue(new_bookingGuest.getContact_email() == "samuel.sessums@smoothstack.com");
    }

    @Test
    @BeforeTestMethod("createBookingGuest")
    @Order(23)
    public void updateBookingGuest() {
        Assertions.assertTrue(adminService.getAllBookingGuests().size() == 1);
        BookingGuest new_bookingGuest = adminService.getAllBookingGuests().get(0);
        new_bookingGuest.setContact_email("new.email@smoothstack.com");
        adminService.saveBookingGuest(new_bookingGuest);
        Assertions.assertTrue(adminService.getAllBookingGuests().size() == 1);
        Assertions.assertTrue(adminService.getAllBookingGuests().get(0).getContact_email() == "new.email@smoothstack.com");
    }

    @Test
    @BeforeTestMethod("createBookingGuest")
    @Order(24)
    public void deleteBookingGuest() {
        Assertions.assertTrue(adminService.getAllBookingGuests().size() == 1);
        adminService.deleteBookingGuest(adminService.getAllBookingGuests().get(0));
        Assertions.assertTrue(adminService.getAllBookingGuests().size() == 0);
    }

    @Test
    @Order(25)
    public void createBookingPayment() {
        Assertions.assertTrue(adminService.getAllBookingPayments().size() == 0);
        adminService.saveBookingPayment(bookingPayment);
        Assertions.assertTrue(adminService.getAllBookingPayments().size() == 1);
    }

    @Test
    @BeforeTestMethod("createBookingPayment")
    @Order(26)
    public void readBookingPayment() {
        Assertions.assertTrue(adminService.getAllBookingPayments().size() == 1);
        BookingPayment new_booking_payment = adminService.getAllBookingPayments().get(0);
        Assertions.assertTrue(new_booking_payment.getRefunded() == false);
    }

    @Test
    @BeforeTestMethod("createBookingPayment")
    @Order(27)
    public void updateBookingPayment() {
        Assertions.assertTrue(adminService.getAllBookingPayments().size() == 1);
        BookingPayment new_booking_payment = adminService.getAllBookingPayments().get(0);
        new_booking_payment.setStripe_id("123456789");
        adminService.saveBookingPayment(new_booking_payment);
        Assertions.assertTrue(adminService.getAllBookingPayments().get(0).getStripe_id() == "123456789");
        Assertions.assertTrue(adminService.getAllBookingPayments().size() == 1);
    }

    @Test
    @BeforeTestMethod("createBookingPayment")
    @Order(28)
    public void deleteBookingPayment() {
        Assertions.assertTrue(adminService.getAllBookingPayments().size() == 1);
        adminService.deleteBookingPayment(bookingPayment);
        Assertions.assertTrue(adminService.getAllBookingPayments().size() == 0);

    }

    @Test
    @Order(29)
    public void createBookingUser() {
        Assertions.assertTrue(adminService.getAllBookingUsers().size() == 0);
        adminService.saveBookingUser(bookingUser);
        Assertions.assertTrue(adminService.getAllBookingUsers().size() == 1);
    }

    @Test
    @BeforeTestMethod("createBookingUser")
    @Order(30)
    public void readBookingUser() {
        Assertions.assertTrue(adminService.getAllBookingUsers().size() == 1);
        BookingUser new_bookingUser = adminService.getAllBookingUsers().get(0);
        logger.info(new_bookingUser.toString());
        Assertions.assertTrue(new_bookingUser.getBookingUserId().equals(new BookingUserId(1, 1)));
    }


    @Test
    @BeforeTestMethod("createBookingUser")
    @Order(31)
    public void updateBookingUser() {
        Assertions.assertTrue(adminService.getAllBookingUsers().size() == 1);
        BookingUser new_bookingUser = adminService.getAllBookingUsers().get(0);
        new_bookingUser.setBookingUserId(new BookingUserId(1, 2));
        Assertions.assertTrue(adminService.getAllBookingUsers().size() == 1);
    }

    @Test
    @BeforeTestMethod("createBookingUser")
    @Order(32)
    public void deleteBookingUser() {
        Assertions.assertTrue(adminService.getAllBookingUsers().size() == 1);
        adminService.deleteBookingUser(adminService.getAllBookingUsers().get(0));
        Assertions.assertTrue(adminService.getAllBookingUsers().size() == 0);

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
        Assertions.assertTrue(flights.size() == 1);
    }

    @Test
    @BeforeTestMethod("createFlight")
    @Order(34)
    public void readFlight() {
        Assertions.assertTrue(adminService.getFlights().size() == 1);
        Flight new_flight = adminService.getFlights().get(0);
        Assertions.assertTrue(new_flight.getRoute().getOriginAirport().getIata_id() == "BAD");
    }

    @Test
    @BeforeTestMethod("createFlight")
    @Order(35)
    public void updateFlight() {
        Assertions.assertTrue(adminService.getFlights().size() == 1);
        Flight new_flight = adminService.getFlights().get(0);
        new_flight.setSeat_price(34f);
        adminService.saveFlight(new_flight);
        Assertions.assertTrue(adminService.getFlights().size() == 1);
        Assertions.assertTrue(adminService.getFlights().get(0).getSeat_price() == 34f);

    }

    @Test
    @BeforeTestMethod("createFlight")
    @Order(36)
    public void deleteFlight() {
        Assertions.assertTrue(adminService.getFlights().size() == 1);
        Flight new_flight = adminService.getFlights().get(0);
        adminService.deleteFlight(new_flight);
        Assertions.assertTrue(adminService.getFlights().size() == 0);
    }

    @Test
    @Order(37)
    public void createFlightBookings() {
        Assertions.assertTrue(adminService.getFlightBookings().size() == 0);
        adminService.saveFlightBooking(flightBookings);
        Assertions.assertTrue(adminService.getFlightBookings().size() == 1);
    }

    @Test
    @BeforeTestMethod("createFlightBookings")
    @Order(38)
    public void readFlightBookings() {
        Assertions.assertTrue(adminService.getFlightBookings().size() == 1);
        FlightBookings new_flightBookings = adminService.getFlightBookings().get(0);
        Assertions.assertTrue(new_flightBookings.getFlightBookingsId().getFlight_id() == 1);
    }

    @Test
    @BeforeTestMethod("createFlightBookings")
    @Order(39)
    public void updateFlightBookings() {
        Assertions.assertTrue(adminService.getFlightBookings().size() == 1);
        FlightBookings new_flightBookings = adminService.getFlightBookings().get(0);
        FlightBookings old_flightBookings = new_flightBookings;
        new_flightBookings.getFlightBookingsId().setFlight_id(1);
        adminService.deleteFlightBooking(old_flightBookings);
        adminService.saveFlightBooking(old_flightBookings);
        Assertions.assertTrue(adminService.getFlightBookings().size() == 1);
        Assertions.assertTrue(adminService.getFlightBookings().get(0).getFlightBookingsId().getBooking_id() == 1);
    }

    @Test
    @BeforeTestMethod("createFlightBookings")
    @Order(40)
    public void deleteFlightBookings() {
        Assertions.assertTrue(adminService.getFlightBookings().size() == 1);
        adminService.deleteFlightBooking(adminService.getFlightBookings().get(0));
        Assertions.assertTrue(adminService.getFlightBookings().size() == 0);


    }

    @Test
    @Order(41)
    public void createPassenger() {
        Assertions.assertTrue(adminService.getPassengers().size() == 0);
        adminService.saveFlightBooking(flightBookings);
        adminService.saveFlight(flight);
        adminService.savePassenger(passenger);
        Assertions.assertTrue(adminService.getPassengers().size() == 1);
    }

    @Test
    @BeforeTestMethod("createPassenger")
    @Order(42)
    public void readPassenger() {
        Assertions.assertTrue(adminService.getPassengers().size() == 1);
        Passenger new_passenger = adminService.getPassengers().get(0);
        Assertions.assertTrue(new_passenger.getId() == 1);
    }

    @Test
    @BeforeTestMethod("createPassenger")
    @Order(43)
    public void updatePassenger() {
        Assertions.assertTrue(adminService.getPassengers().size() == 1);
        Passenger new_passenger = adminService.getPassengers().get(0);
        new_passenger.setGender("female");
        adminService.savePassenger(new_passenger);
        Assertions.assertTrue(adminService.getPassengers().size() == 1);
        Assertions.assertTrue(adminService.getPassengers().get(0).getGender().equals("female"));

    }

    @Test
    @BeforeTestMethod("createPassenger")
    @Order(44)
    public void deletePassenger() {
        Assertions.assertTrue(adminService.getPassengers().size() == 1);
        adminService.deletePassenger(adminService.getPassengers().get(0));
        Assertions.assertTrue(adminService.getPassengers().size() == 0);

    }

    @Test
    @Order(45)
    public void createRoute() {
        Assertions.assertTrue(adminService.getRoutes().size() == 0);
        adminService.saveRoute(route);
        Assertions.assertTrue(adminService.getRoutes().size() == 1);

    }

    @Test
    @BeforeTestMethod("createRoute")
    @Order(47)
    public void readRoute() {
        Assertions.assertTrue(adminService.getRoutes().size() == 1);
        Route new_route = adminService.getRoutes().get(0);
        logger.info(new_route.toString());
        Assertions.assertTrue(new_route.getOriginAirport().getIata_id().equals("GNV"));
    }

    @Test
    @BeforeTestMethod("createRoute")
    @Order(48)
    public void updateRoute() {
        Assertions.assertTrue(adminService.getRoutes().size() == 1);
        Route new_route = adminService.getRoutes().get(0);
        new_route.getOriginAirport().setIata_id("SAD");
        adminService.saveRoute(new_route);
        Assertions.assertTrue(adminService.getRoutes().size() == 1);
        Assertions.assertTrue(adminService.getRoutes().get(0).getOriginAirport().getIata_id().equals("SAD"));
    }

    @Test
    @BeforeTestMethod("createRoute")
    @Order(49)
    public void deleteRoute() {
        Assertions.assertTrue(adminService.getRoutes().size() == 1);
        adminService.deleteRoute(adminService.getRoutes().get(0));
        Assertions.assertTrue(adminService.getRoutes().size() == 0);
    }

    @Test
    @Order(50)
    public void createUser() {
        Assertions.assertTrue(adminService.getAllUsers().size() == 0);
        adminService.saveUser(user);
        Assertions.assertTrue(adminService.getAllUsers().size() == 1);
    }

    @Test
    @BeforeTestMethod("createUser")
    @Order(51)
    public void readUser() {
        Assertions.assertTrue(adminService.getAllUsers().size() == 1);
        User new_user = adminService.getAllUsers().get(0);
        Assertions.assertTrue(new_user.getFamily_name().equals("Sessums"));
    }

    @Test
    @BeforeTestMethod("createUser")
    @Order(52)
    public void updateUser() {
        Assertions.assertTrue(adminService.getAllUsers().size() == 1);
        User new_user = adminService.getAllUsers().get(0);
        new_user.setPassword("Kufh09ufjnf09u");
        adminService.saveUser(new_user);
        Assertions.assertTrue(adminService.getAllUsers().size() == 1);
        Assertions.assertTrue(adminService.getAllUsers().get(0).getPassword().equals("Kufh09ufjnf09u"));

    }

    @Test
    @BeforeTestMethod("createUser")
    @Order(53)
    public void deleteUser() {
        Assertions.assertTrue(adminService.getAllUsers().size() == 1);
        adminService.deleteUser(adminService.getAllUsers().get(0));
        Assertions.assertTrue(adminService.getAllUsers().size() == 0);
    }

    @Test
    @Order(54)
    public void createUserRole() {
        Assertions.assertTrue(adminService.getAllUserRoles().size() == 0);
        adminService.saveUserRole(userRole);
        Assertions.assertTrue(adminService.getAllUserRoles().size() == 1);

    }

    @Test
    @BeforeTestMethod("createUserRole")
    @Order(55)
    public void readUserRole() {
        Assertions.assertTrue(adminService.getAllUserRoles().size() == 1);
        UserRole new_userRole = adminService.getAllUserRoles().get(0);
        Assertions.assertTrue(new_userRole.getName().equals("user"));
    }

    @Test
    @BeforeTestMethod("createUserRole")
    @Order(56)
    public void updateUserRole() {
        Assertions.assertTrue(adminService.getAllUserRoles().size() == 1);
        UserRole new_userRole = adminService.getAllUserRoles().get(0);
        new_userRole.setName("Benevolent Dictator For Life");
        adminService.saveUserRole(new_userRole);
        Assertions.assertTrue(adminService.getAllUserRoles().size() == 1);
        Assertions.assertTrue(adminService.getAllUserRoles().get(0).getName().equals("Benevolent Dictator For Life"));
    }

    @Test
    @BeforeTestMethod("createUserRole")
    @Order(57)
    public void deleteUserRole() {
        Assertions.assertTrue(adminService.getAllUserRoles().size() == 1);
        adminService.deleteUserRole(adminService.getAllUserRoles().get(0));
        Assertions.assertTrue(adminService.getAllUserRoles().size() == 0);
    }


}
