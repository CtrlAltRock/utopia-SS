package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.*;
import com.smoothstack.ua.security.jwt.JwtRequest;
import com.smoothstack.ua.security.jwt.JwtResponse;
import com.smoothstack.ua.security.jwt.JwtTokenUtil;
import com.smoothstack.ua.security.jwt.JwtUserDetailsService;
import com.smoothstack.ua.security.models.UserDTO;
import com.smoothstack.ua.services.AdminService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin
public class AdminController {



    @Autowired
    AdminService adminService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    private User user;


    @Timed("authenticate")
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        user = adminService.getUserByUsername(authenticationRequest.getUsername());
        adminService.setUser(user);
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Timed("register")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.airplanes.dump")
    @RequestMapping(path = "utopia/airlines/get/airplanes/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<Airplane> getAirplanes() {
        return adminService.getAllAirplanes();
    }

    @Timed("get.airplanes.id")
    @RequestMapping(path = "utopia/airlines/get/airplanes/id/{airplaneId}", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public Airplane getAirplaneById(@PathVariable Integer airplaneId) { return adminService.getAirplaneById(airplaneId); }

    @Timed("get.airplanes.airplaneType")
    @RequestMapping(path = "utopia/airlines/get/airplanes/airplane_type/{airplane_type}", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<Airplane> getAirplanesByAirplaneTypeId(@PathVariable Integer airplane_type) { return adminService.getAirplaneByAirplaneTypeId(airplane_type); }

    @Timed("get.airplanes.maxCapacity")
    @RequestMapping(path = "utopia/airlines/get/airplanes/max_capacity/{max_capacity}", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<Airplane> getAirplanesByMaxCapacity(@PathVariable Integer max_capacity) { return adminService.getAirplaneByMaxCapacity(max_capacity); }

    @Timed("post.airplanes")
    @RequestMapping(path = "utopia/airlines/post/airplanes", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveAirplanes(@RequestBody List<Airplane> airplanes) {
        adminService.saveAirplanes(airplanes);
    }

    @Timed("post.airplane")
    @RequestMapping(path = "utopia/airlines/post/airplane", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveAirplane(@RequestBody Airplane airplane) {
        adminService.saveAirplane(airplane);
    }

    @Timed("delete.airplanes")
    @RequestMapping(path = "utopia/airlines/delete/airplanes", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteAirplanes(@RequestBody List<Airplane> airplanes) { adminService.deleteAirplanes(airplanes); }

    @Timed("delete.airplane")
    @RequestMapping(path = "utopia/airlines/delete/airplane/id/{airplaneId}", method = RequestMethod.DELETE)
    public void deleteAirplaneById(@PathVariable Integer airplaneId) { adminService.deleteAirplaneById(airplaneId); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.airplaneTypes.dump")
    @RequestMapping(path = "utopia/airlines/get/airplaneTypes/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<AirplaneType> getAirplaneTypes() {
        return adminService.getALLAirplaneTypes();
    }

    @Timed("post.airplaneTypes")
    @RequestMapping(path = "utopia/airlines/post/airplaneTypes", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveAirplaneTypes(@RequestBody List<AirplaneType> airplaneTypes) { adminService.saveAirplaneTypes(airplaneTypes); }

    @Timed("post.airplaneType")
    @RequestMapping(path = "utopia/airlines/post/airplaneType", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveAirplaneType(@RequestBody AirplaneType airplaneType) { adminService.saveAirplaneType(airplaneType);}

    @Timed("delete.airplaneTypes")
    @RequestMapping(path = "utopia/airlines/delete/airplaneTypes", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteAirplaneTypes(List<AirplaneType> airplaneTypes) { adminService.deleteAirplaneTypes(airplaneTypes); }

    @Timed("delete.airplaneType")
    @RequestMapping(path = "utopia/airlines/delete/airplaneType", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteAirplaneType(AirplaneType airplaneType) { adminService.deleteAirplaneType(airplaneType); }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.airports.dump")
    @RequestMapping(path = "utopia/airlines/get/airports/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<Airport> getAirports() {
        return adminService.getAllAirports();
    }

    @Timed("post.airports")
    @RequestMapping(path = "utopia/airlines/post/airports", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveAirports(@RequestBody List<Airport> airports) {
        adminService.saveAirports(airports);
    }

    @Timed("post.airport")
    @RequestMapping(path = "utopia/airlines/post/airport", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveAirport(@RequestBody Airport airport) {
        adminService.saveAirport(airport);
    }

    @Timed("delete.airports")
    @RequestMapping(path = "utopia/airlines/delete/airports", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"} )
    public void deleteAirports(@RequestBody List<Airport> airports) { adminService.deleteAirports(airports); }

    @Timed("delete.airport")
    @RequestMapping(path = "utopia/airlines/delete/airport", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"} )
    public void deleteAirport(@RequestBody Airport airport) { adminService.deleteAirport(airport); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.bookings.dump")
    @RequestMapping(path = "utopia/airlines/get/bookings/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<Booking> getBookings() {
        return adminService.getAllBookings();
    }

    @Timed("post.bookings")
    @RequestMapping(path = "utopia/airlines/post/bookings", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveBookings(@RequestBody List<Booking> bookings) {
        adminService.saveBookings(bookings);
    }

    @Timed("post.booking")
    @RequestMapping(path = "utopia/airlines/post/booking", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveBooking(@RequestBody Booking booking) {
        adminService.saveBooking(booking);
    }

    @Timed("delete.bookings")
    @RequestMapping(path = "utopia/airlines/delete/bookings", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteBookings(@RequestBody List<Booking> bookings) {
        adminService.deleteBookings(bookings);
    }

    @Timed("delete.booking")
    @RequestMapping(path = "utopia/airlines/delete/booking", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteBooking(@RequestBody Booking booking) { adminService.deleteBooking(booking); }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.bookingAgents.dump")
    @RequestMapping(path = "utopia/airlines/get/bookingAgents/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<BookingAgent> getBookingAgents() { return adminService.getALlBookingAgents(); }

    @Timed("post.bookingAgents")
    @RequestMapping(path = "utopia/airlines/post/bookingAgents", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveBookingAgents(@RequestBody List<BookingAgent> bookingAgents) { adminService.saveBookingAgents(bookingAgents); }

    @Timed("post.bookingAgent")
    @RequestMapping(path = "utopia/airlines/post/bookingAgent", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveBookingAgent(@RequestBody BookingAgent bookingAgent) { adminService.saveBookingAgent(bookingAgent); }

    @Timed("delete.bookingAgents")
    @RequestMapping(path = "utopia/airlines/delete/bookingAgents", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteBookingAgents(@RequestBody List<BookingAgent> bookingAgents) { adminService.deleteBookingAgents(bookingAgents); }

    @Timed("delete.bookingAgents")
    @RequestMapping(path = "utopia/airlines/delete/bookingAgent", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteBookingAgent(@RequestBody BookingAgent bookingAgent) { adminService.deleteBookingAgent(bookingAgent); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.bookingGuests.dump")
    @RequestMapping(path = "utopia/airlines/get/bookingGuests/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<BookingGuest> getBookingGuests() { return adminService.getAllBookingGuests(); }

    @Timed("post.bookingGuests")
    @RequestMapping(path = "utopia/airlines/post/bookingGuests", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveBookingGuests(@RequestBody List<BookingGuest> bookingGuests) { adminService.saveBookingGuests(bookingGuests); }

    @Timed("post.bookingGuest")
    @RequestMapping(path = "utopia/airlines/post/bookingGuest", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveBookingGuest(@RequestBody BookingGuest bookingGuest) { adminService.saveBookingGuest(bookingGuest); }

    @Timed("delete.bookingGuests")
    @RequestMapping(path = "utopia/airlines/delete/bookingGuests", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteBookingGuests(@RequestBody List<BookingGuest> bookingGuests) { adminService.deleteBookingGuests(bookingGuests); }

    @Timed("delete.bookingGuest")
    @RequestMapping(path = "utopia/airlines/delete/bookingGuest", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteBookingGuest(@RequestBody BookingGuest bookingGuest) { adminService.deleteBookingGuest(bookingGuest); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.bookingPayments.dump")
    @RequestMapping(path = "utopia/airlines/get/bookingPayments/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<BookingPayment> getBookingPayments() { return adminService.getAllBookingPayments(); }

    @Timed("post.bookingPayments")
    @RequestMapping(path = "utopia/airlines/post/bookingPayments", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveBookingPayments(@RequestBody List<BookingPayment> bookingPayments) { adminService.saveBookingPayments(bookingPayments); }

    @Timed("post.bookingPayment")
    @RequestMapping(path = "utopia/airlines/post/bookingPayment", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveBookingPayment(@RequestBody BookingPayment bookingPayment) { adminService.saveBookingPayment(bookingPayment); }

    @Timed("delete.bookingPayments")
    @RequestMapping(path = "utopia/airlines/delete/bookingPayments", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteBookingPayments(@RequestBody List<BookingPayment> bookingPayments) { adminService.deleteBookingPayments(bookingPayments); }

    @Timed("delete.bookingPayment")
    @RequestMapping(path = "utopia/airlines/delete/bookingPayment", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteBookingPayment(@RequestBody BookingPayment bookingPayment) { adminService.deleteBookingPayment(bookingPayment); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.bookingUsers.dump")
    @RequestMapping(path = "utopia/airlines/get/bookingUsers/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<BookingUser> getBookingUsers() { return adminService.getAllBookingUsers(); }

    @Timed("post.bookingUsers")
    @RequestMapping(path = "utopia/airlines/post/bookingUsers", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveBookingUsers(@RequestBody List<BookingUser> bookingUsers) { adminService.saveBookingUsers(bookingUsers); }

    @Timed("post.bookingUser")
    @RequestMapping(path = "utopia/airlines/post/bookingUser", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveBookingUser(@RequestBody BookingUser bookingUser) { adminService.saveBookingUser(bookingUser); }

    @Timed("delete.bookingUsers")
    @RequestMapping(path = "utopia/airlines/delete/bookingUsers", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteBookingUsers(@RequestBody List<BookingUser> bookingUsers) { adminService.deleteBookingUsers(bookingUsers); }

    @Timed("delete.bookingUser")
    @RequestMapping(path = "utopia/airlines/delete/bookingUser", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteBookingUser(@RequestBody BookingUser bookingUser) { adminService.deleteBookingUser(bookingUser); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.flights.dump")
    @RequestMapping(path = "utopia/airlines/get/flights/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<Flight> getFlights() { return adminService.getFlights(); }

    @Timed("get.flights.route.id")
    @RequestMapping(path = "utopia/airlines/get/flights/routeId/{routeId}", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<Flight> getFlightsByRouteId(@PathVariable Integer routeId) { return adminService.getFlightsByRouteId(routeId); }

    @Timed("get.flights.airplane.id")
    @RequestMapping(path = "utopia/airlines/get/flights/airplaneId/{airplaneId}", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<Flight> getFlightsByAirplaneId(@PathVariable Integer airplaneId) { return adminService.getFlightsByAirplaneId(airplaneId); }

    @Timed("post.flights")
    @RequestMapping(path = "utopia/airlines/post/flights", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveFlights(@RequestBody List<Flight> flights) { adminService.saveFlights(flights); }

    @Timed("post.flight")
    @RequestMapping(path = "utopia/airlines/post/flight", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveFlight(@RequestBody Flight flight) { adminService.saveFlight(flight); }

    @Timed("delete.flights")
    @RequestMapping(path = "utopia/airlines/delete/flights", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteFlights(@RequestBody List<Flight> flights) { adminService.deleteFlights(flights); }

    @Timed("delete.flight")
    @RequestMapping(path = "utopia/airlines/delete/flight", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteFlight(@RequestBody Flight flight) { adminService.deleteFlight(flight); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.flightBookings.dump")
    @RequestMapping(path = "utopia/airlines/get/flightBookings/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<FlightBookings> getFlightBookings() { return adminService.getFlightBookings(); }

    @Timed("post.flightBookings")
    @RequestMapping(path = "utopia/airlines/post/flightBookings", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveFlightBookings(@RequestBody List<FlightBookings> flightBookings) { adminService.saveFlightBookings(flightBookings); }

    @Timed("post.flightBooking")
    @RequestMapping(path = "utopia/airlines/post/flightBooking", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveFlightBooking(@RequestBody FlightBookings flightBooking) { adminService.saveFlightBooking(flightBooking); }

    @Timed("delete.flightBookings")
    @RequestMapping(path = "utopia/airlines/delete/flightBookings", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteFlightBookings(@RequestBody List<FlightBookings> flightBookings) { adminService.deleteFlightBookings(flightBookings); }

    @Timed("delete.flightBooking")
    @RequestMapping(path = "utopia/airlines/delete/flightBooking", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteFlightBooking(@RequestBody FlightBookings flightBooking) { adminService.deleteFlightBooking(flightBooking); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.passengers.dump")
    @RequestMapping(path = "utopia/airlines/get/passengers/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<Passenger> getPassengers() { return adminService.getPassengers(); }

    @Timed("get.passengers.id")
    @RequestMapping(path = "utopia/airline/get/passengers/id/{passengerId}", method = RequestMethod.GET)
    public Passenger getPassengerById(@PathVariable Integer passengerId) { return adminService.getPassengerById(passengerId); }

    @Timed("get.passengers.booking.id")
    @RequestMapping(path = "utopia/airline/get/passengers/booking_id/{bookingId}", method = RequestMethod.GET)
    public List<Passenger> getPassengersByBookingId(@PathVariable Integer bookingId) { return adminService.getPassengersByBookingId(bookingId); }

    @Timed("get.passengers.familyName")
    @RequestMapping(path = "utopia/airline/get/passengers/familyName/{familyName}", method = RequestMethod.GET)
    public List<Passenger> getPassengersByFamilyName(@PathVariable String familyName) { return adminService.getPassengersByFamilyName(familyName); }

    @Timed("post.passengers")
    @RequestMapping(path = "utopia/airlines/post/passengers", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void savePassengers(@RequestBody List<Passenger> passengers) { adminService.savePassengers(passengers); }

    @Timed("post.passenger")
    @RequestMapping(path = "utopia/airlines/post/passenger", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void savePassengers(@RequestBody Passenger passenger) { adminService.savePassenger(passenger); }

    @Timed("delete.passengers")
    @RequestMapping(path = "utopia/airlines/delete/passengers", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deletePassengers(@RequestBody List<Passenger> passengers) { adminService.deletePassengers(passengers); }

    @Timed("delete.passenger")
    @RequestMapping(path = "utopia/airlines/delete/passenger", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deletePassenger(@RequestBody Passenger passenger) { adminService.deletePassenger(passenger); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.routes.dump")
    @RequestMapping(path = "utopia/airlines/get/routes/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<Route> getRoutes() { return adminService.getRoutes(); }

    @Timed("post.routes")
    @RequestMapping(path = "utopia/airlines/post/routes", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveRoutes(@RequestBody List<Route> routes) { adminService.saveRoutes(routes); }

    @Timed("post.route")
    @RequestMapping(path = "utopia/airlines/post/route", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveRoute(@RequestBody Route route) { adminService.saveRoute(route); }

    @Timed("delete.routes")
    @RequestMapping(path = "utopia/airlines/delete/routes", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteRoutes(@RequestBody List<Route> routes) { adminService.deleteRoutes(routes); }

    @Timed("delete.route")
    @RequestMapping(path = "utopia/airlines/delete/route", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteRoute(@RequestBody Route route) { adminService.deleteRoute(route); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.users.dump")
    @RequestMapping(path = "utopia/airlines/get/users/dump", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public List<User> getUsers() { return adminService.getAllUsers(); }

    @Timed("post.users")
    @RequestMapping(path = "utopia/airlines/post/users", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void postUsers(@RequestBody List<User> users) { adminService.saveUsers(users); }

    @Timed("post.user")
    @RequestMapping(path = "utopia/airlines/post/user", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void postUser(@RequestBody User user) { adminService.saveUser(user); }

    @Timed("delete.users")
    @RequestMapping(path = "utopia/airlines/delete/users", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteUsers(@RequestBody List<User> users) { adminService.deleteUsers(users); }

    @Timed("delete.user")
    @RequestMapping(path = "utopia/airlines/delete/user", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteUser(@RequestBody User user) { adminService.deleteUser(user); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.userRoles.dump")
    @RequestMapping(path = "utopia/airlines/get/userRoles/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<UserRole> getUserRoles() { return adminService.getAllUserRoles(); }

    @Timed("post.userRoles")
    @RequestMapping(path = "utopia/airlines/post/userRoles", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void postUserRoles(@RequestBody List<UserRole> userRoles) { adminService.saveUserRoles(userRoles); }

    @Timed("post.userRole")
    @RequestMapping(path = "utopia/airlines/post/userRole", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void postUserRole(@RequestBody UserRole userRole) { adminService.saveUserRole(userRole); }

    @Timed("delete.userRoles")
    @RequestMapping(path = "utopia/airlines/delete/userRoles", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteUserRoles(@RequestBody List<UserRole> userRoles) { adminService.deleteUserRoles(userRoles); }

    @Timed("delete.userRole")
    @RequestMapping(path = "utopia/airlines/delete/userRole", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteUserRole(@RequestBody UserRole userRole) { adminService.deleteUserRole(userRole); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
