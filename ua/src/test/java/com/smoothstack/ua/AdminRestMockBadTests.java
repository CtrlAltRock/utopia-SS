package com.smoothstack.ua;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smoothstack.ua.models.*;
import com.smoothstack.ua.security.jwt.JwtUserDetailsService;
import com.smoothstack.ua.services.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminRestMockBadTests {
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
        flight.setDeparture_time(LocalDateTime.parse("2022-12-26T00:00:00"));
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

    @MockBean
    AirplaneService airplaneService;

    @MockBean
    AirplaneTypeService airplaneTypeService;

    @MockBean
    AirportService airportService;

    @MockBean
    BookingAgentService bookingAgentService;

    @MockBean
    BookingService bookingService;

    @MockBean
    BookingPaymentService bookingPaymentService;

    @MockBean
    BookingUserService bookingUserService;

    @MockBean
    FlightBookingService flightBookingService;

    @MockBean
    FlightService flightService;

    @MockBean
    PassengerService passengerService;

    @MockBean
    RouteService routeService;

    @MockBean
    UserService userService;

    @MockBean
    UserRoleService userRoleService;

    @MockBean
    private JwtUserDetailsService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void postAirplane() throws Exception {
        Mockito.when(airplaneService.saveAirplane(any(Airplane.class)))
                .thenReturn(airplane);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/airplanes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new Airplane())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAirplaneById() throws Exception {
        Mockito.when(airplaneService.getAirplaneById(1))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/airplanes/1/"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putAirplaneBadId() throws Exception {
        Mockito.when(airplaneService.getAirplaneById(1))
                .thenReturn(null);

        Mockito.when(airplaneService.getAirplaneTypeById(airplane.getAirplaneType().getId()))
                .thenReturn(airplane.getAirplaneType());

        Mockito.when(airplaneService.updateAirplane(any(Airplane.class)))
                .thenReturn(airplane);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/airplanes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(airplane)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putAirplaneChangeType() throws Exception {
        Mockito.when(airplaneService.getAirplaneById(1))
                .thenReturn(airplane);

        Mockito.when(airplaneService.getAirplaneTypeById(airplane.getAirplaneType().getId()))
                .thenReturn(new AirplaneType());

        Mockito.when(airplaneService.updateAirplane(any(Airplane.class)))
                .thenReturn(airplane);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/airplanes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(airplane)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteAirplane() throws Exception {
        Mockito.when(airplaneService.getAirplaneById(1))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/airplanes/1/"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postAirplaneType() throws Exception {
        Mockito.when(airplaneTypeService.saveAirplaneType(null))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/airplaneTypes/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void getAirplaneTypesById() throws Exception {
        Mockito.when(airplaneTypeService.getAirplaneTypeById(1))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/airplaneTypes/1"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void putAirplaneTypeBadId() throws Exception {
        Mockito.when(airplaneTypeService.getAirplaneTypeById(1))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/airplaneTypes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void deleteAirplaneType() throws Exception {
        Mockito.when(airplaneTypeService.getAirplaneTypeById(1))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/airplaneTypes/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postAirport() throws Exception {
        Mockito.when(airportService.getAirportById(origin_airport.getIata_id()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/airports/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAirportById() throws Exception {
        Mockito.when(airportService.getAirportById(origin_airport.getIata_id()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/airports/GNV")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putAirport() throws Exception {
        Mockito.when(airportService.getAirportById(destination_airport.getIata_id()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/airports/BWI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(destination_airport)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteAirport() throws Exception{
        Mockito.when(airportService.getAirportById(destination_airport.getIata_id()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/airports/BWI")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void postBookingAgent() throws Exception {
        Mockito.when(bookingAgentService.getBookingAgentById(bookingAgentId))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/bookingAgents/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookingAgent)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getBookingAgentsById() throws Exception {
        Mockito.when(bookingAgentService.getBookingAgentById(bookingAgentId))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/bookingAgents/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteBookingAgent() throws Exception {
        Mockito.when(bookingAgentService.getBookingAgentById(bookingAgentId))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/bookingAgents/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postBooking() throws Exception {
        Mockito.when(bookingService.saveBooking(booking))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/bookings/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void getBookingsById() throws Exception {
        Mockito.when(bookingService.getBookingsById(booking.getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putBookings() throws Exception {
        Mockito.when(bookingService.getBookingsById(booking.getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(booking)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteBookings() throws Exception {
        Mockito.when(bookingService.getBookingsById(booking.getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postBookingPayments() throws Exception {
        Mockito.when(bookingPaymentService.getBookingPaymentById(bookingPaymentId.getBooking_id()))
                .thenReturn(bookingPayment);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/bookingPayments/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookingPayment)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void getBookingPaymentsById() throws Exception {
        Mockito.when(bookingPaymentService.getBookingPaymentById(bookingPayment.getBookingPaymentId().getBooking_id()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/bookingPayments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putBookingPayments() throws Exception {
        Mockito.when(bookingPaymentService.getBookingPaymentById(bookingPayment.getBookingPaymentId().getBooking_id()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/bookingPayments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookingPayment)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteBookingPayments() throws Exception {
        Mockito.when(bookingPaymentService.getBookingPaymentById(bookingPayment.getBookingPaymentId().getBooking_id()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/bookingPayments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postBookingUsers() throws Exception {
        Mockito.when(bookingUserService.getBookingUserById(bookingUserId))
                .thenReturn(bookingUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/bookingUsers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookingUser)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void getBookingUsersById() throws Exception {
        Mockito.when(bookingUserService.getBookingUserById(bookingUserId))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/bookingUsers/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putBookingUsers() throws Exception {
        Mockito.when(bookingUserService.getBookingUserById(bookingUserId))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/bookingUsers/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookingUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteBookingUsers() throws Exception {
        Mockito.when(bookingUserService.getBookingUserById(bookingUserId))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/bookingUsers/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postFlightBookingsExists() throws Exception {
        Mockito.when(flightBookingService.getFlightBookingsById(flightBookings.getFlightBookingsId()))
                .thenReturn(flightBookings);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/flightBookings/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(flightBookings)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postFlightBookingsBadFlight() throws Exception {
        Mockito.when(flightBookingService.getFlightBookingsById(flightBookings.getFlightBookingsId()))
                .thenReturn(null);

        Mockito.when(flightService.getFlightById(flightBookings.getFlightBookingsId().getFlight_id()))
                .thenReturn(null);

        Mockito.when(bookingService.getBookingsById(flightBookings.getFlightBookingsId().getBooking_id()))
                .thenReturn(booking);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/flightBookings/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(flightBookings)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postFlightBookingsBadBooking() throws Exception {
        Mockito.when(flightBookingService.getFlightBookingsById(flightBookings.getFlightBookingsId()))
                .thenReturn(null);

        Mockito.when(flightService.getFlightById(flightBookings.getFlightBookingsId().getFlight_id()))
                .thenReturn(flight);

        Mockito.when(bookingService.getBookingsById(flightBookings.getFlightBookingsId().getBooking_id()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/flightBookings/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(flightBookings)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getFlightBookingsById() throws Exception {
        Mockito.when(flightBookingService.getFlightBookingsById(flightBookings.getFlightBookingsId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/flightBookings/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putFlightBookingsBadId() throws Exception {
        Mockito.when(flightBookingService.getFlightBookingsById(flightBookings.getFlightBookingsId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/flightBookings/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(flightBookings))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putFlightBookingsBadFlight() throws Exception {
        Mockito.when(flightBookingService.getFlightBookingsById(flightBookings.getFlightBookingsId()))
                .thenReturn(flightBookings);

        Mockito.when(flightService.getFlightById(flightBookings.getFlightBookingsId().getFlight_id()))
                .thenReturn(null);

        Mockito.when(bookingService.getBookingsById(flightBookings.getFlightBookingsId().getBooking_id()))
                .thenReturn(booking);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/flightBookings/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(flightBookings))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putFlightBookings() throws Exception {
        Mockito.when(flightBookingService.getFlightBookingsById(flightBookings.getFlightBookingsId()))
                .thenReturn(flightBookings);

        Mockito.when(flightService.getFlightById(flightBookings.getFlightBookingsId().getFlight_id()))
                .thenReturn(flight);

        Mockito.when(bookingService.getBookingsById(flightBookings.getFlightBookingsId().getBooking_id()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/flightBookings/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(flightBookings))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteFlightBookingsBadId() throws Exception {
        Mockito.when(flightBookingService.getFlightBookingsById(flightBookings.getFlightBookingsId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/flightBookings/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void postFlightsIdIncluded() throws Exception {
        Mockito.when(routeService.getRouteById(flight.getRoute().getId()))
                .thenReturn(route);

        Mockito.when(airplaneService.getAirplaneById(flight.getAirplane().getId()))
                .thenReturn(flight.getAirplane());


        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/flights/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(flight))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void postFlightsBadRoute() throws Exception {
        flight.setId(null);
        Mockito.when(routeService.getRouteById(flight.getRoute().getId()))
                .thenReturn(null);

        Mockito.when(airplaneService.getAirplaneById(flight.getAirplane().getId()))
                .thenReturn(flight.getAirplane());

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/flights/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(flight))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void postFlightsBadAirplane() throws Exception {
        flight.setId(null);
        Mockito.when(routeService.getRouteById(flight.getRoute().getId()))
                .thenReturn(route);

        Mockito.when(airplaneService.getAirplaneById(flight.getAirplane().getId()))
                .thenReturn(null);


        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/flights/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(flight))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void getFlightsById() throws Exception {
        Mockito.when(flightService.getFlightById(flight.getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/flights/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void putFlightsBadId() throws Exception {
        Mockito.when(flightService.getFlightById(flight.getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/flights/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(flight))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putFlightsIdIncluded() throws Exception {
        Mockito.when(flightService.getFlightById(flight.getId()))
                .thenReturn(flight);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/flights/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(flight))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putFlightsBadRoute() throws Exception {
        Mockito.when(flightService.getFlightById(flight.getId()))
                .thenReturn(flight);

        Mockito.when(routeService.getRouteById(flight.getRoute().getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/flights/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(flight))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putFlightsBadAirplane() throws Exception {
        Mockito.when(flightService.getFlightById(flight.getId()))
                .thenReturn(flight);

        Mockito.when(routeService.getRouteById(flight.getRoute().getId()))
                .thenReturn(flight.getRoute());

        Mockito.when(airplaneService.getAirplaneById(flight.getAirplane().getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/flights/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(flight))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteFlights() throws Exception {
        Mockito.when(flightService.getFlightById(flight.getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/flights/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void postPassengersIdIncluded() throws Exception {
        Mockito.when(bookingService.getBookingsById(passenger.getBooking_id().getId()))
                .thenReturn(passenger.getBooking_id());

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/passengers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(passenger))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postPassengersBadBookingId() throws Exception {
        passenger.setId(null);

        Mockito.when(bookingService.getBookingsById(passenger.getBooking_id().getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/passengers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(passenger))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getPassengersById() throws Exception {
        Mockito.when(passengerService.getPassengerById(passenger.getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/passengers/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void putPassengersBadId() throws Exception {
        Mockito.when(passengerService.getPassengerById(passenger.getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/passengers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(passenger))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deletePassengers() throws Exception {
        Mockito.when(passengerService.getPassengerById(passenger.getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/passengers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postRoutesExists() throws Exception {
        Mockito.when(routeService.getRouteByOriginDestination(route.getOriginAirport().getIata_id(), route.getDestinationAirport().getIata_id()))
                .thenReturn(route);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/routes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(route))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postRoutesBadOrigin() throws Exception {
        Mockito.when(routeService.getRouteByOriginDestination(route.getOriginAirport().getIata_id(), route.getDestinationAirport().getIata_id()))
                .thenReturn(null);

        Mockito.when(airportService.getAirportById(route.getOriginAirport().getIata_id()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/routes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(route))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postRoutesBadDestination() throws Exception {
        Mockito.when(routeService.getRouteByOriginDestination(route.getOriginAirport().getIata_id(), route.getDestinationAirport().getIata_id()))
                .thenReturn(null);

        Mockito.when(airportService.getAirportById(route.getOriginAirport().getIata_id()))
                .thenReturn(origin_airport);

        Mockito.when(airportService.getAirportById(route.getDestinationAirport().getIata_id()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/routes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(route))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getRoutesById() throws Exception {
        Mockito.when(routeService.getRouteById(route.getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/routes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putRoutesBadId() throws Exception {
        Mockito.when(routeService.getRouteById(route.getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/routes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(route))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putRoutesBadOrigin() throws Exception {
        Mockito.when(routeService.getRouteById(route.getId()))
                .thenReturn(route);

        Mockito.when(airportService.getAirportById(route.getOriginAirport().getIata_id()))
                .thenReturn(null);

        Mockito.when(airportService.getAirportById(route.getDestinationAirport().getIata_id()))
                .thenReturn(route.getDestinationAirport());

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/routes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(route))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putRoutesBadDestination() throws Exception {
        Mockito.when(routeService.getRouteById(route.getId()))
                .thenReturn(route);

        Mockito.when(airportService.getAirportById(route.getOriginAirport().getIata_id()))
                .thenReturn(route.getOriginAirport());

        Mockito.when(airportService.getAirportById(route.getDestinationAirport().getIata_id()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/routes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(route))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteRoutes() throws Exception {
        Mockito.when(routeService.getRouteById(route.getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/routes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUsersIncludedId() throws Exception {
        Mockito.when(userRoleService.getUserRoleById(user.getUserRole().getId()))
                .thenReturn(user.getUserRole());

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUsersBadRole() throws Exception {
        user.setId(null);

        Mockito.when(userRoleService.getUserRoleById(user.getUserRole().getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUsersById() throws Exception {
        Mockito.when(userService.getUserById(user.getId().intValue()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void putUsersBadId() throws Exception {
        Mockito.when(userService.getUserById(user.getId().intValue()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putUsersIncludedId() throws Exception {
        Mockito.when(userService.getUserById(user.getId().intValue()))
                .thenReturn(user);

        Mockito.when(userRoleService.getUserRoleById(user.getUserRole().getId()))
                .thenReturn(user.getUserRole());

        Mockito.when(userService.saveUser(user))
                .thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putUsersBadRole() throws Exception {
        Mockito.when(userService.getUserById(user.getId().intValue()))
                .thenReturn(user);

        user.setId(null);

        Mockito.when(userRoleService.getUserRoleById(user.getUserRole().getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putUsersChangedRole() throws Exception {
        Mockito.when(userService.getUserById(user.getId().intValue()))
                .thenReturn(user);

        user.setId(null);

        Mockito.when(userRoleService.getUserRoleById(user.getUserRole().getId()))
                .thenReturn(new UserRole());

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUsers() throws Exception {
        Mockito.when(userService.getUserById(user.getId().intValue()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUserRolesIncludedId() throws Exception {
        Mockito.when(userRoleService.getUserRoleById(userRole.getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/userRoles/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRole))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUserRolesExists() throws Exception {
        userRole.setId(null);

        Mockito.when(userRoleService.getUserRoleByName(userRole.getName()))
                .thenReturn(userRole);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/userRoles/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRole))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUserRolesById() throws Exception {
        Mockito.when(userRoleService.getUserRoleById(userRole.getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/userRoles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putUserRolesIncludedId() throws Exception {
        Mockito.when(userRoleService.getUserRoleById(userRole.getId()))
                .thenReturn(userRole);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/userRoles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRole))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putUserRolesBadId() throws Exception {
        Mockito.when(userRoleService.getUserRoleById(userRole.getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/userRoles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRole))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUserRoles() throws Exception {
        Mockito.when(userRoleService.getUserRoleById(userRole.getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/userRoles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
