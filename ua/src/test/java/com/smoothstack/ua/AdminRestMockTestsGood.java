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
public class AdminRestMockTestsGood {

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
    public void postAirplaneGood() throws Exception {
        Mockito.when(airplaneService.saveAirplane(any(Airplane.class)))
				.thenReturn(airplane);

        Mockito.when(airplaneService.getAirplaneTypeById(airplaneType.getId()))
				.thenReturn(airplaneType);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/airplanes/").contentType(MediaType.APPLICATION_JSON).content(asJsonString(airplane)))
                .andExpect(status().isOk());
    }

    @Test
    public void postAirplaneBad() throws Exception {
        Mockito.when(airplaneService.saveAirplane(any(Airplane.class)))
				.thenReturn(airplane);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/airplanes/").contentType(MediaType.APPLICATION_JSON).content(asJsonString(new Airplane())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllAirplanes() throws Exception {
        Mockito.when(airplaneService.getAllAirplanes())
				.thenReturn(Arrays.asList(new Airplane(), new Airplane(), new Airplane()));

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/airplanes/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAirplaneById() throws Exception {
        Mockito.when(airplaneService.getAirplaneById(1))
				.thenReturn(airplane);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/airplanes/1/"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void putAirplane() throws Exception {
        Mockito.when(airplaneService.getAirplaneById(1))
				.thenReturn(airplane);

        Mockito.when(airplaneService.getAirplaneTypeById(airplane.getAirplaneType().getId()))
				.thenReturn(airplane.getAirplaneType());

        Mockito.when(airplaneService.updateAirplane(any(Airplane.class)))
				.thenReturn(airplane);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/airplanes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(airplane)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAirplane() throws Exception {
        Mockito.when(airplaneService.getAirplaneById(1))
				.thenReturn(airplane);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/airplanes/1/"))
                .andExpect(status().isOk());
    }

    @Test
    public void postAirplaneType() throws Exception {
        Mockito.when(airplaneTypeService.saveAirplaneType(airplaneType))
				.thenReturn(airplaneType);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/airplaneTypes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(airplaneType)))
                .andExpect(status().isOk());
    }
    
    @Test
    public void getAirplaneTypes() throws Exception {
        Mockito.when(airplaneTypeService.getAllAirplaneTypes())
				.thenReturn(Arrays.asList(airplaneType));

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/airplaneTypes/"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAirplaneTypesById() throws Exception {
        Mockito.when(airplaneTypeService.getAirplaneTypeById(1))
				.thenReturn(airplaneType);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/airplaneTypes/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void putAirplaneType() throws Exception {
        Mockito.when(airplaneTypeService.getAirplaneTypeById(1))
				.thenReturn(airplaneType);

        airplaneType.setMax_capacity(45);

        Mockito.when(airplaneTypeService.saveAirplaneType(airplaneType))
				.thenReturn(airplaneType);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/airplaneTypes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(airplaneType)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAirplaneType() throws Exception {
        Mockito.when(airplaneTypeService.getAirplaneTypeById(1))
				.thenReturn(airplaneType);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/airplaneTypes/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void postAirport() throws Exception {
        Mockito.when(airportService.getAirportById(origin_airport.getIata_id()))
				.thenReturn(null);

        Mockito.when(airportService.saveAirport(origin_airport))
				.thenReturn(origin_airport);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/airports/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(origin_airport)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllAirport() throws Exception {
        Mockito.when(airportService.getAllAirports())
				.thenReturn(Arrays.asList(origin_airport, destination_airport));

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/airports/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAirportById() throws Exception {
        Mockito.when(airportService.getAirportById(origin_airport.getIata_id()))
				.thenReturn(origin_airport);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/airports/GNV")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void putAirport() throws Exception {
        Mockito.when(airportService.getAirportById(destination_airport.getIata_id()))
				.thenReturn(destination_airport);

        Mockito.when(airportService.saveAirport(destination_airport))
				.thenReturn(destination_airport);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/airports/BWI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(destination_airport)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAirport() throws Exception{
        Mockito.when(airportService.getAirportById(destination_airport.getIata_id()))
				.thenReturn(destination_airport);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/airports/BWI")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void postBookingAgent() throws Exception {
        Mockito.when(bookingAgentService.getBookingAgentById(bookingAgentId))
				.thenReturn(bookingAgent);

        Mockito.when(bookingAgentService.saveBookingAgent(bookingAgent))
				.thenReturn(bookingAgent);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/bookingAgents/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookingAgent)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllBookingAgents() throws Exception {
        Mockito.when(bookingAgentService.getAllBookingAgents())
				.thenReturn(Arrays.asList(bookingAgent));

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/bookingAgents/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getBookingAgentsById() throws Exception {
        Mockito.when(bookingAgentService.getBookingAgentById(bookingAgentId))
				.thenReturn(bookingAgent);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/bookingAgents/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteBookingAgent() throws Exception {
        Mockito.when(bookingAgentService.getBookingAgentById(bookingAgentId))
				.thenReturn(bookingAgent);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/bookingAgents/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void postBooking() throws Exception {
        Mockito.when(bookingService.saveBooking(booking))
				.thenReturn(booking);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/bookings/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(booking)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllBookings() throws Exception {
        Mockito.when(bookingService.getAllBookings())
				.thenReturn(Arrays.asList(booking));

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/bookings/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getBookingsById() throws Exception {
        Mockito.when(bookingService.getBookingsById(booking.getId()))
				.thenReturn(booking);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void putBookings() throws Exception {
        Mockito.when(bookingService.getBookingsById(booking.getId()))
				.thenReturn(booking);

        Mockito.when(bookingService.saveBooking(booking))
				.thenReturn(booking);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(booking)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteBookings() throws Exception {
        Mockito.when(bookingService.getBookingsById(booking.getId()))
				.thenReturn(booking);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void postBookingPayments() throws Exception {
        Mockito.when(bookingPaymentService.getBookingPaymentById(bookingPaymentId.getBooking_id()))
				.thenReturn(null);

        Mockito.when(bookingPaymentService.saveBookingPayment(bookingPayment))
				.thenReturn(bookingPayment);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/bookingPayments/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookingPayment)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void getAllBookingPayments() throws Exception {
        Mockito.when(bookingPaymentService.getAllBookingPayments())
				.thenReturn(Arrays.asList(bookingPayment));

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/bookingPayments/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getBookingPaymentsById() throws Exception {
        Mockito.when(bookingPaymentService.getBookingPaymentById(bookingPayment.getBookingPaymentId().getBooking_id()))
				.thenReturn(bookingPayment);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/bookingPayments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void putBookingPayments() throws Exception {
        Mockito.when(bookingPaymentService.getBookingPaymentById(bookingPayment.getBookingPaymentId().getBooking_id()))
				.thenReturn(bookingPayment);

        Mockito.when(bookingPaymentService.saveBookingPayment(bookingPayment))
				.thenReturn(bookingPayment);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/bookingPayments/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(bookingPayment)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteBookingPayments() throws Exception {
        Mockito.when(bookingPaymentService.getBookingPaymentById(bookingPayment.getBookingPaymentId().getBooking_id()))
				.thenReturn(bookingPayment);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/bookingPayments/1")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void postBookingUsers() throws Exception {
        Mockito.when(bookingUserService.getBookingUserById(bookingUserId))
                .thenReturn(null);
        
        Mockito.when(bookingUserService.saveBookingUser(bookingUser))
                .thenReturn(bookingUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/bookingUsers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookingUser)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllBookingUsers() throws Exception {
        Mockito.when(bookingUserService.getAllBookingUsers())
                .thenReturn(Arrays.asList(bookingUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/bookingUsers/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getBookingUsersById() throws Exception {
        Mockito.when(bookingUserService.getBookingUserById(bookingUserId))
                .thenReturn(bookingUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/bookingUsers/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void putBookingUsers() throws Exception {
        Mockito.when(bookingUserService.getBookingUserById(bookingUserId))
                .thenReturn(bookingUser);

        Mockito.when(bookingUserService.saveBookingUser(bookingUser))
                .thenReturn(bookingUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/bookingUsers/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookingUser)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBookingUsers() throws Exception {
        Mockito.when(bookingUserService.getBookingUserById(bookingUserId))
                .thenReturn(bookingUser);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/bookingUsers/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void postFlightBookings() throws Exception {
        Mockito.when(flightBookingService.getFlightBookingsById(flightBookings.getFlightBookingsId()))
                .thenReturn(null);

        Mockito.when(flightService.getFlightById(flightBookings.getFlightBookingsId().getFlight_id()))
				.thenReturn(flight);

        Mockito.when(bookingService.getBookingsById(flightBookings.getFlightBookingsId().getBooking_id()))
				.thenReturn(booking);

        Mockito.when(flightBookingService.saveFlightBooking(flightBookings))
				.thenReturn(flightBookings);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/flightBookings/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(flightBookings)))
                .andExpect(status().isOk());

    }

    @Test
    public void getAllFlightBookings() throws Exception {
        Mockito.when(flightBookingService.getFlightBookings())
				.thenReturn(Arrays.asList(flightBookings));

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/flightBookings/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(1)));

    }

    @Test
    public void getFlightBookingsById() throws Exception {
        Mockito.when(flightBookingService.getFlightBookingsById(flightBookings.getFlightBookingsId()))
				.thenReturn(flightBookings);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/flightBookings/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void putFlightBookings() throws Exception {
        Mockito.when(flightBookingService.getFlightBookingsById(flightBookings.getFlightBookingsId()))
				.thenReturn(flightBookings);

        Mockito.when(flightService.getFlightById(flightBookings.getFlightBookingsId().getFlight_id()))
				.thenReturn(flight);

        Mockito.when(bookingService.getBookingsById(flightBookings.getFlightBookingsId().getBooking_id()))
				.thenReturn(booking);

        Mockito.when(flightBookingService.saveFlightBooking(flightBookings))
				.thenReturn(flightBookings);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/flightBookings/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(flightBookings))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFlightBookings() throws Exception {
        Mockito.when(flightBookingService.getFlightBookingsById(flightBookings.getFlightBookingsId()))
				.thenReturn(flightBookings);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/flightBookings/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void postFlights() throws Exception {
        flight.setId(null);
        Mockito.when(routeService.getRouteById(flight.getRoute().getId()))
				.thenReturn(flight.getRoute());

        Mockito.when(airplaneService.getAirplaneById(flight.getAirplane().getId()))
				.thenReturn(flight.getAirplane());

        Mockito.when(flightService.saveFlight(flight))
				.thenReturn(flight);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/flights/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(flight))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void getAllFlights() throws Exception {
        Mockito.when(flightService.getFlights())
				.thenReturn(Arrays.asList(flight));

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/flights/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getFlightsById() throws Exception {
        Mockito.when(flightService.getFlightById(flight.getId()))
				.thenReturn(flight);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/flights/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void putFlights() throws Exception {
        Mockito.when(flightService.getFlightById(flight.getId()))
				.thenReturn(flight);

        flight.setId(null);

        Mockito.when(routeService.getRouteById(flight.getRoute().getId()))
				.thenReturn(flight.getRoute());

        Mockito.when(airplaneService.getAirplaneById(flight.getAirplane().getId()))
				.thenReturn(flight.getAirplane());

        Mockito.when(flightService.saveFlight(flight))
				.thenReturn(flight);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/flights/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(flight))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFlights() throws Exception {
        Mockito.when(flightService.getFlightById(flight.getId()))
				.thenReturn(flight);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/flights/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void postPassengers() throws Exception {
        passenger.setId(null);

        Mockito.when(bookingService.getBookingsById(passenger.getBooking_id().getId()))
				.thenReturn(passenger.getBooking_id());

        Mockito.when(passengerService.savePassenger(passenger))
				.thenReturn(passenger);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/passengers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(passenger))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllPassengers() throws Exception {
        Mockito.when(passengerService.getPassengers())
				.thenReturn(Arrays.asList(passenger));

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/passengers/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getPassengersById() throws Exception {
        Mockito.when(passengerService.getPassengerById(passenger.getId()))
				.thenReturn(passenger);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/passengers/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void putPassengers() throws Exception {
        Mockito.when(passengerService.getPassengerById(passenger.getId()))
                .thenReturn(passenger);

        Mockito.when(bookingService.getBookingsById(passenger.getBooking_id().getId()))
                .thenReturn(booking);

        Mockito.when(passengerService.savePassenger(passenger))
                .thenReturn(passenger);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/passengers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(passenger))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePassengers() throws Exception {
        Mockito.when(passengerService.getPassengerById(passenger.getId()))
                .thenReturn(passenger);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/passengers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void postRoutes() throws Exception {
        Mockito.when(routeService.getRouteByOriginDestination(route.getOriginAirport().getIata_id(), route.getDestinationAirport().getIata_id()))
                .thenReturn(null);

        Mockito.when(airportService.getAirportById(route.getOriginAirport().getIata_id()))
                .thenReturn(origin_airport);

        Mockito.when(airportService.getAirportById(route.getDestinationAirport().getIata_id()))
                .thenReturn(destination_airport);

        Mockito.when(routeService.saveRoute(route))
                .thenReturn(route);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/routes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(route))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllRoutes() throws Exception {
        Mockito.when(routeService.getRoutes())
                .thenReturn(Arrays.asList(route));

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/routes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getRoutesById() throws Exception {
        Mockito.when(routeService.getRouteById(route.getId()))
                .thenReturn(route);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/routes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void putRoutes() throws Exception {
        Mockito.when(routeService.getRouteById(route.getId()))
                .thenReturn(route);

        Mockito.when(airportService.getAirportById(route.getOriginAirport().getIata_id()))
                .thenReturn(route.getOriginAirport());

        Mockito.when(airportService.getAirportById(route.getDestinationAirport().getIata_id()))
                .thenReturn(route.getDestinationAirport());

        Mockito.when(routeService.saveRoute(route))
                .thenReturn(route);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/routes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(route))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRoutes() throws Exception {
        Mockito.when(routeService.getRouteById(route.getId()))
                .thenReturn(route);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/routes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void postUsers() throws Exception {
        user.setId(null);

        Mockito.when(userRoleService.getUserRoleById(user.getUserRole().getId()))
                .thenReturn(user.getUserRole());

        Mockito.when(userService.saveUser(user))
                .thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getAllUsers() throws Exception {
        user.setId(null);

        Mockito.when(userService.getAllUsers())
                .thenReturn(Arrays.asList(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getUsersById() throws Exception {
        Mockito.when(userService.getUserById(user.getId().intValue()))
                .thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void putUsers() throws Exception {
        Mockito.when(userService.getUserById(user.getId().intValue()))
                .thenReturn(user);

        user.setId(null);

        Mockito.when(userRoleService.getUserRoleById(user.getUserRole().getId()))
                .thenReturn(user.getUserRole());

        Mockito.when(userService.saveUser(user))
                .thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUsers() throws Exception {
        Mockito.when(userService.getUserById(user.getId().intValue()))
                .thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void postUserRoles() throws Exception {
        userRole.setId(null);

        Mockito.when(userRoleService.getUserRoleById(userRole.getId()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/userRoles/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRole))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllUserRoles() throws Exception {
        Mockito.when(userRoleService.getAllUserRoles())
                .thenReturn(Arrays.asList(userRole));

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/userRoles/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserRolesById() throws Exception {
        Mockito.when(userRoleService.getUserRoleById(userRole.getId()))
                .thenReturn(userRole);

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/userRoles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void putUserRoles() throws Exception {

        Mockito.when(userRoleService.getUserRoleById(userRole.getId()))
                .thenReturn(userRole);

        userRole.setId(null);

        Mockito.when(userRoleService.saveUserRole(userRole))
                .thenReturn(userRole);

        mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/userRoles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRole))
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserRoles() throws Exception {
        Mockito.when(userRoleService.getUserRoleById(userRole.getId()))
                .thenReturn(userRole);

        mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/userRoles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
