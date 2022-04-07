package com.smoothstack.ua;

import com.smoothstack.ua.models.*;
import com.smoothstack.ua.services.*;
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

@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {UaApplication.class})
public class AdminCrudTests {

    Logger logger = LoggerFactory.getLogger(AdminCrudTests.class);

    @Autowired
    AirplaneService airplaneService;

    @Autowired
    AirplaneTypeService airplaneTypeService;

    @Autowired
    AirportService airportService;

    @Autowired
    BookingAgentService bookingAgentService;

    @Autowired
    BookingService bookingService;

    @Autowired
    BookingPaymentService bookingPaymentService;

    @Autowired
    BookingUserService bookingUserService;

    @Autowired
    FlightBookingService flightBookingService;

    @Autowired
    FlightService flightService;

    @Autowired
    PassengerService passengerService;

    @Autowired
    RouteService routeService;

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    Airplane airplane;
    AirplaneType airplaneType;
    Airport origin_airport;
    Airport destination_airport;
    Booking booking;
    BookingAgent bookingAgent;
    BookingAgentId bookingAgentId;
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
        airplaneTypeService.saveAirplaneType(airplaneType);
        Assertions.assertTrue(airplaneTypeService.getAllAirplaneTypes().size() == 1);
    }


    @Test
    @BeforeTestMethod("createAirplaneType")
    @Order(2)
    public void readAirplaneType() {
        AirplaneType airplaneType = airplaneTypeService.getAllAirplaneTypes().get(0);
        Assertions.assertTrue(airplaneType.getId() == 1);

    }



    @Test
    @BeforeTestMethod("createAirplaneType")
    @Order(3)
    public void updateAirplaneType() {
        AirplaneType airplaneType = airplaneTypeService.getAllAirplaneTypes().get(0);
        Assertions.assertTrue(airplaneType.getId() == 1);
        airplaneType.setMax_capacity(23);
        airplaneTypeService.saveAirplaneType(airplaneType);
        airplaneType = airplaneTypeService.getAllAirplaneTypes().get(0);
        Assertions.assertTrue(airplaneType.getMax_capacity() == 23);
    }


    @Test
    @BeforeTestMethod("createAirplaneType")
    @Order(4)
    public void deleteAirplaneType() {
        AirplaneType airplaneType = airplaneTypeService.getAllAirplaneTypes().get(0);
        airplaneTypeService.deleteAirplaneTypeById(airplaneType.getId());
        Assertions.assertTrue(airplaneTypeService.getAllAirplaneTypes().size() == 0);
    }


    @Test
    @Order(5)
    public void createAirplane() {
        airplaneService.saveAirplane(airplane);
        Assertions.assertTrue(airplaneService.getAllAirplanes().size() == 1);
    }

    @Test
    @BeforeTestMethod("createAirplane")
    @Order(6)
    public void readAirplane() {
        Assertions.assertTrue(airplaneService.getAllAirplanes().size() == 1);
        Airplane new_airplane = airplaneService.getAllAirplanes().get(0);
        logger.info(new_airplane.toString());
        logger.info(airplaneTypeService.getAllAirplaneTypes().toString());
        //Assertions.assertTrue(airplane.getAirplaneType().equals(airplaneType));
        Assertions.assertTrue(new_airplane.getId() == 1);
    }

    @Test
    @BeforeTestMethod("readAirplane")
    @Order(7)
    public void updateAirplane() {
        Airplane new_airplane = airplaneService.getAllAirplanes().get(0);
        new_airplane.setAirplaneType(airplaneType);
        airplaneService.saveAirplane(new_airplane);
        Assertions.assertTrue(airplaneService.getAllAirplanes().size() == 1);
        Assertions.assertTrue(airplaneService.getAllAirplanes().get(0).getId() == 1);

    }

    @Test
    @BeforeTestMethod("updateAirplane")
    @Order(8)
    public void deleteAirplane() {
        airplaneService.deleteAirplane(airplaneService.getAirplaneById(1));
        Assertions.assertTrue(airplaneService.getAllAirplanes().size() == 0);
    }

    @Test
    @Order(9)
    public void createAirport() {
        airportService.saveAirport(origin_airport);
        Assertions.assertTrue(airportService.getAllAirports().size() == 1);
    }

    @Test
    @BeforeTestMethod("createAirport")
    @Order(10)
    public void readAirport() {
        Assertions.assertTrue(airportService.getAllAirports().size() == 1);
        Airport airport = airportService.getAllAirports().get(0);
    }

    @Test
    @BeforeTestMethod("createAirport")
    @Order(11)
    public void updateAirport() {
        Airport airport = airportService.getAllAirports().get(0);
        airport.setCity("new city");
        airportService.saveAirport(airport);
        Assertions.assertTrue(airportService.getAllAirports().size() == 1);
    }

    @Test
    @BeforeTestMethod("updateAirport")
    @Order(12)
    public void deleteAirport() {
        Assertions.assertTrue(airportService.getAllAirports().size() == 1);
        airportService.deleteAirport(airportService.getAllAirports().get(0));
        Assertions.assertTrue(airportService.getAllAirports().size() == 0);
    }

    @Test
    @Order(13)
    public void createBooking() {
        Assertions.assertTrue(bookingService.getAllBookings().size() == 0);
        bookingService.saveBooking(booking);
        Assertions.assertTrue(bookingService.getAllBookings().size() == 1);

    }

    @Test
    @BeforeTestMethod("createBooking")
    @Order(14)
    public void readBooking() {
        Assertions.assertTrue(bookingService.getAllBookings().size() == 1);
        Booking new_booking = bookingService.getAllBookings().get(0);
        Assertions.assertTrue(new_booking.getIs_active() == true);

    }

    @Test
    @BeforeTestMethod("createBooking")
    @Order(15)
    public void updateBooking() {
        Assertions.assertTrue(bookingService.getAllBookings().size() == 1);
        Booking new_booking = bookingService.getAllBookings().get(0);
        new_booking.setIs_active(false);
        bookingService.saveBooking(new_booking);
        Assertions.assertTrue(bookingService.getAllBookings().size() == 1);
        Assertions.assertTrue(bookingService.getAllBookings().get(0).getIs_active() == false);
    }

    @Test
    @BeforeTestMethod("updateBooking")
    @Order(16)
    public void deleteBooking() {
        Assertions.assertTrue(bookingService.getAllBookings().size() == 1);
        bookingService.deleteBookingById(bookingService.getAllBookings().get(0).getId());
        Assertions.assertTrue(bookingService.getAllBookings().size() == 0);

    }

    @Test
    @Order(17)
    public void createBookingAgent() {
        Assertions.assertTrue(bookingAgentService.getAllBookingAgents().size() == 0);
        bookingAgentService.saveBookingAgent(bookingAgent);
        Assertions.assertTrue(bookingAgentService.getAllBookingAgents().size() == 1);
    }

    @Test
    @BeforeTestMethod("createBookingAgent")
    @Order(18)
    public void readBookingAgent() {
        Assertions.assertTrue(bookingAgentService.getAllBookingAgents().size() == 1);
        BookingAgent new_bookingAgent = bookingAgentService.getAllBookingAgents().get(0);
        logger.info(new_bookingAgent.toString());
        Assertions.assertTrue(new_bookingAgent.getBookingAgentId().getAgent_id() == bookingAgentId.getAgent_id());

    }

    @Test
    @BeforeTestMethod("createBookingAgent")
    @Order(19)
    public void updateBookingAgent() {
        Assertions.assertTrue(bookingAgentService.getAllBookingAgents().size() == 1);
        BookingAgent new_bookingAgent = bookingAgentService.getAllBookingAgents().get(0);
        new_bookingAgent.getBookingAgentId().setBooking_id(1);
        bookingAgentService.saveBookingAgent(new_bookingAgent);
        Assertions.assertTrue(bookingAgentService.getAllBookingAgents().size() == 1);

    }

    @Test
    @BeforeTestMethod("createBookingAgent")
    @Order(20)
    public void deleteBookingAgent() {
        Assertions.assertTrue(bookingAgentService.getAllBookingAgents().size() == 1);
        bookingAgentService.deleteBookingAgent(bookingAgentService.getAllBookingAgents().get(0));
        Assertions.assertTrue(bookingAgentService.getAllBookingAgents().size() == 0);


    }

    @Test
    @Order(25)
    public void createBookingPayment() {
        Assertions.assertTrue(bookingPaymentService.getAllBookingPayments().size() == 0);
        bookingPaymentService.saveBookingPayment(bookingPayment);
        Assertions.assertTrue(bookingPaymentService.getAllBookingPayments().size() == 1);
    }

    @Test
    @BeforeTestMethod("createBookingPayment")
    @Order(26)
    public void readBookingPayment() {
        Assertions.assertTrue(bookingPaymentService.getAllBookingPayments().size() == 1);
        BookingPayment new_booking_payment = bookingPaymentService.getAllBookingPayments().get(0);
        Assertions.assertTrue(new_booking_payment.getRefunded() == false);
    }

    @Test
    @BeforeTestMethod("createBookingPayment")
    @Order(27)
    public void updateBookingPayment() {
        Assertions.assertTrue(bookingPaymentService.getAllBookingPayments().size() == 1);
        BookingPayment new_booking_payment = bookingPaymentService.getAllBookingPayments().get(0);
        new_booking_payment.setStripe_id("123456789");
        bookingPaymentService.saveBookingPayment(new_booking_payment);
        Assertions.assertTrue(bookingPaymentService.getAllBookingPayments().get(0).getStripe_id() == "123456789");
        Assertions.assertTrue(bookingPaymentService.getAllBookingPayments().size() == 1);
    }

    @Test
    @BeforeTestMethod("createBookingPayment")
    @Order(28)
    public void deleteBookingPayment() {
        Assertions.assertTrue(bookingPaymentService.getAllBookingPayments().size() == 1);
        bookingPaymentService.deleteBookingPayment(bookingPayment);
        Assertions.assertTrue(bookingPaymentService.getAllBookingPayments().size() == 0);

    }

    @Test
    @Order(29)
    public void createBookingUser() {
        Assertions.assertTrue(bookingUserService.getAllBookingUsers().size() == 0);
        bookingUserService.saveBookingUser(bookingUser);
        Assertions.assertTrue(bookingUserService.getAllBookingUsers().size() == 1);
    }

    @Test
    @BeforeTestMethod("createBookingUser")
    @Order(30)
    public void readBookingUser() {
        Assertions.assertTrue(bookingUserService.getAllBookingUsers().size() == 1);
        BookingUser new_bookingUser = bookingUserService.getAllBookingUsers().get(0);
        logger.info(new_bookingUser.toString());
        Assertions.assertTrue(new_bookingUser.getBookingUserId().equals(new BookingUserId(1, 1)));
    }


    @Test
    @BeforeTestMethod("createBookingUser")
    @Order(31)
    public void updateBookingUser() {
        Assertions.assertTrue(bookingUserService.getAllBookingUsers().size() == 1);
        BookingUser new_bookingUser = bookingUserService.getAllBookingUsers().get(0);
        new_bookingUser.setBookingUserId(new BookingUserId(1, 2));
        Assertions.assertTrue(bookingUserService.getAllBookingUsers().size() == 1);
    }

    @Test
    @BeforeTestMethod("createBookingUser")
    @Order(32)
    public void deleteBookingUser() {
        Assertions.assertTrue(bookingUserService.getAllBookingUsers().size() == 1);
        bookingUserService.deleteBookingUser(bookingUserService.getAllBookingUsers().get(0));
        Assertions.assertTrue(bookingUserService.getAllBookingUsers().size() == 0);

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

        flightService.saveFlight(flight);
        List<Flight> flights = flightService.getFlights();
        Assertions.assertTrue(flights.size() == 1);
    }

    @Test
    @BeforeTestMethod("createFlight")
    @Order(34)
    public void readFlight() {
        Assertions.assertTrue(flightService.getFlights().size() == 1);
        Flight new_flight = flightService.getFlights().get(0);
        Assertions.assertTrue(new_flight.getRoute().getOriginAirport().getIata_id() == "BAD");
    }

    @Test
    @BeforeTestMethod("createFlight")
    @Order(35)
    public void updateFlight() {
        Assertions.assertTrue(flightService.getFlights().size() == 1);
        Flight new_flight = flightService.getFlights().get(0);
        new_flight.setSeat_price(34f);
        flightService.saveFlight(new_flight);
        Assertions.assertTrue(flightService.getFlights().size() == 1);
        Assertions.assertTrue(flightService.getFlights().get(0).getSeat_price() == 34f);

    }

    @Test
    @BeforeTestMethod("createFlight")
    @Order(36)
    public void deleteFlight() {
        Assertions.assertTrue(flightService.getFlights().size() == 1);
        Flight new_flight = flightService.getFlights().get(0);
        flightService.deleteFlightById(new_flight.getId());
        Assertions.assertTrue(flightService.getFlights().size() == 0);
    }

    @Test
    @Order(37)
    public void createFlightBookings() {
        Assertions.assertTrue(flightBookingService.getFlightBookings().size() == 0);
        flightBookingService.saveFlightBooking(flightBookings);
        Assertions.assertTrue(flightBookingService.getFlightBookings().size() == 1);
    }

    @Test
    @BeforeTestMethod("createFlightBookings")
    @Order(38)
    public void readFlightBookings() {
        Assertions.assertTrue(flightBookingService.getFlightBookings().size() == 1);
        FlightBookings new_flightBookings = flightBookingService.getFlightBookings().get(0);
        Assertions.assertTrue(new_flightBookings.getFlightBookingsId().getFlight_id() == 1);
    }

    @Test
    @BeforeTestMethod("createFlightBookings")
    @Order(39)
    public void updateFlightBookings() {
        Assertions.assertTrue(flightBookingService.getFlightBookings().size() == 1);
        FlightBookings new_flightBookings = flightBookingService.getFlightBookings().get(0);
        FlightBookings old_flightBookings = new_flightBookings;
        new_flightBookings.getFlightBookingsId().setFlight_id(1);
        flightBookingService.deleteFlightBooking(old_flightBookings);
        flightBookingService.saveFlightBooking(old_flightBookings);
        Assertions.assertTrue(flightBookingService.getFlightBookings().size() == 1);
        Assertions.assertTrue(flightBookingService.getFlightBookings().get(0).getFlightBookingsId().getBooking_id() == 1);
    }

    @Test
    @BeforeTestMethod("createFlightBookings")
    @Order(40)
    public void deleteFlightBookings() {
        Assertions.assertTrue(flightBookingService.getFlightBookings().size() == 1);
        flightBookingService.deleteFlightBooking(flightBookingService.getFlightBookings().get(0));
        Assertions.assertTrue(flightBookingService.getFlightBookings().size() == 0);


    }

    @Test
    @Order(41)
    public void createPassenger() {
        Assertions.assertTrue(passengerService.getPassengers().size() == 0);
        flightBookingService.saveFlightBooking(flightBookings);
        flightBookingService.getFlightBookingsById(flightBookingsId);
        flightService.saveFlight(flight);
        passengerService.savePassenger(passenger);
        Assertions.assertTrue(passengerService.getPassengers().size() == 1);
    }

    @Test
    @BeforeTestMethod("createPassenger")
    @Order(42)
    public void readPassenger() {
        Assertions.assertTrue(passengerService.getPassengers().size() == 1);
        Passenger new_passenger = passengerService.getPassengers().get(0);
        logger.info(new_passenger.toString());
        Assertions.assertTrue(new_passenger.getId() == 1);
    }

    @Test
    @BeforeTestMethod("createPassenger")
    @Order(43)
    public void updatePassenger() {
        Assertions.assertTrue(passengerService.getPassengers().size() == 1);
        Passenger new_passenger = passengerService.getPassengers().get(0);
        logger.info(new_passenger.toString());
        new_passenger.setGender("female");
        new_passenger.setBooking_id(booking);
        System.out.println(new_passenger.toString());
        passengerService.savePassenger(new_passenger);
        Assertions.assertTrue(passengerService.getPassengers().size() == 1);
        Assertions.assertTrue(passengerService.getPassengers().get(0).getGender().equals("female"));

    }

    @Test
    @BeforeTestMethod("createPassenger")
    @Order(44)
    public void deletePassenger() {
        Assertions.assertTrue(passengerService.getPassengers().size() == 1);
        passengerService.deletePassenger(passengerService.getPassengers().get(0));
        //passengerService.deletePassengerById(passengerService.getPassengers().get(0).getId());
        Assertions.assertTrue(passengerService.getPassengers().size() == 0);

    }

    @Test
    @Order(45)
    public void createRoute() {
        System.out.println(routeService.getRoutes());
        Assertions.assertTrue(routeService.getRoutes().size() == 1);
        routeService.saveRoute(route);
        Assertions.assertTrue(routeService.getRoutes().size() == 2);

    }

    @Test
    @BeforeTestMethod("createRoute")
    @Order(47)
    public void readRoute() {
        Assertions.assertTrue(routeService.getRoutes().size() == 2);
        Route new_route = routeService.getRoutes().get(0);
        logger.info(new_route.toString());
        Assertions.assertTrue(new_route.getOriginAirport().getIata_id().equals("GNV"));
    }

    @Test
    @BeforeTestMethod("createRoute")
    @Order(48)
    public void updateRoute() {
        Assertions.assertTrue(routeService.getRoutes().size() == 2);
        Route new_route = routeService.getRoutes().get(0);
        new_route.getOriginAirport().setIata_id("SAD");
        routeService.saveRoute(new_route);
        Assertions.assertTrue(routeService.getRoutes().size() == 2);
        Assertions.assertTrue(routeService.getRoutes().get(0).getOriginAirport().getIata_id().equals("SAD"));
    }

    @Test
    @BeforeTestMethod("createRoute")
    @Order(49)
    public void deleteRoute() {
        Assertions.assertTrue(routeService.getRoutes().size() == 2);
        Route route = routeService.getRoutes().get(0);
        route.setDestinationAirport(null);
        route.setOriginAirport(null);
        flight.setRoute(null);
        flightService.saveFlight(flight);
        routeService.saveRoute(route);
        routeService.deleteRoute(route);
        Assertions.assertTrue(routeService.getRoutes().size() == 1);
    }

    @Test
    @Order(50)
    public void createUser() {
        Assertions.assertTrue(userService.getAllUsers().size() == 0);
        userService.saveUser(user);
        Assertions.assertTrue(userService.getAllUsers().size() == 1);
    }

    @Test
    @BeforeTestMethod("createUser")
    @Order(51)
    public void readUser() {
        Assertions.assertTrue(userService.getAllUsers().size() == 1);
        User new_user = userService.getAllUsers().get(0);
        Assertions.assertTrue(new_user.getFamily_name().equals("Sessums"));
    }

    @Test
    @BeforeTestMethod("createUser")
    @Order(52)
    public void updateUser() {
        Assertions.assertTrue(userService.getAllUsers().size() == 1);
        User new_user = userService.getAllUsers().get(0);
        new_user.setPassword("Kufh09ufjnf09u");
        userService.saveUser(new_user);
        Assertions.assertTrue(userService.getAllUsers().size() == 1);
        Assertions.assertTrue(!userService.getAllUsers().get(0).getPassword().equals("Kufh09ufjnf09u"));

    }

    @Test
    @BeforeTestMethod("createUser")
    @Order(53)
    public void deleteUser() {
        Assertions.assertTrue(userService.getAllUsers().size() == 1);
        userService.deleteUserById(userService.getAllUsers().get(0).getId());
        Assertions.assertTrue(userService.getAllUsers().size() == 0);
    }

    @Test
    @Order(54)
    public void createUserRole() {
        Assertions.assertTrue(userRoleService.getAllUserRoles().size() == 0);
        userRoleService.saveUserRole(userRole);
        Assertions.assertTrue(userRoleService.getAllUserRoles().size() == 1);

    }

    @Test
    @BeforeTestMethod("createUserRole")
    @Order(55)
    public void readUserRole() {
        Assertions.assertTrue(userRoleService.getAllUserRoles().size() == 1);
        UserRole new_userRole = userRoleService.getAllUserRoles().get(0);
        Assertions.assertTrue(new_userRole.getName().equals("user"));
    }

    @Test
    @BeforeTestMethod("createUserRole")
    @Order(56)
    public void updateUserRole() {
        Assertions.assertTrue(userRoleService.getAllUserRoles().size() == 1);
        UserRole new_userRole = userRoleService.getAllUserRoles().get(0);
        new_userRole.setName("Benevolent Dictator For Life");
        userRoleService.saveUserRole(new_userRole);
        Assertions.assertTrue(userRoleService.getAllUserRoles().size() == 1);
        Assertions.assertTrue(userRoleService.getAllUserRoles().get(0).getName().equals("Benevolent Dictator For Life"));
    }

    @Test
    @BeforeTestMethod("createUserRole")
    @Order(57)
    public void deleteUserRole() {
        Assertions.assertTrue(userRoleService.getAllUserRoles().size() == 1);
        userRoleService.deleteUserRoleById(userRoleService.getAllUserRoles().get(0).getId());
        Assertions.assertTrue(userRoleService.getAllUserRoles().size() == 0);
    }


}
