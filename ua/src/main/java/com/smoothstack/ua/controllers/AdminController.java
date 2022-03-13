package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.*;
import com.smoothstack.ua.services.AdminService;
import com.smoothstack.ua.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;


    @RequestMapping(path = "utopia/airlines/get/airplanes/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<Airplane> getAirplanes() {
        return adminService.getAllAirplanes();
    }

    @RequestMapping(path = "utopia/airlines/post/airplanes", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveAirplanes(@RequestBody List<Airplane> airplanes) {
        adminService.saveAirplanes(airplanes);
    }

    @RequestMapping(path = "utopia/airlines/get/airplaneTypes/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<AirplaneType> getAirplaneTypes() {
        return adminService.getALLAirplaneTypes();
    }

    @RequestMapping(path = "utopia/airlines/post/airplaneTypes", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveAirplaneTypes(@RequestBody List<AirplaneType> airplaneTypes) { adminService.saveAirplaneTypes(airplaneTypes); }

    @RequestMapping(path = "utopia/airlines/get/airports/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<Airport> getAirports() {
        return adminService.getAllAirports();
    }

    @RequestMapping(path = "utopia/airlines/post/airports", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveAirports(@RequestBody List<Airport> airports) {
        adminService.saveAirports(airports);
    }

    @RequestMapping(path = "utopia/airlines/get/bookings/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<Booking> getBookings() {
        return adminService.getAllBookings();
    }

    @RequestMapping(path = "utopia/airlines/post/bookings", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveBookings(@RequestBody List<Booking> bookings) {
        adminService.saveBookings(bookings);
    }

    @RequestMapping(path = "utopia/airlines/get/bookingAgents/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<BookingAgent> getBookingAgents() { return adminService.getALlBookingAgents(); }

    @RequestMapping(path = "utopia/airlines/post/bookingAgents", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveBookingAgents(@RequestBody List<BookingAgent> bookingAgents) { adminService.saveBookingAgents(bookingAgents); }

    @RequestMapping(path = "utopia/airlines/get/bookingGuests/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<BookingGuest> getBookingGuests() { return adminService.getAllBookingGuests(); }

    @RequestMapping(path = "utopia/airlines/post/bookingGuests", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveBookingGuests(@RequestBody List<BookingGuest> bookingGuests) { adminService.saveBookingGuests(bookingGuests); }

    @RequestMapping(path = "utopia/airlines/get/bookingPayments/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<BookingPayment> getBookingPayments() { return adminService.getAllBookingPayments(); }

    @RequestMapping(path = "utopia/airlines/post/bookingPayments", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveBookingPayments(@RequestBody List<BookingPayment> bookingPayments) { adminService.saveBookingPayment(bookingPayments); }

    @RequestMapping(path = "utopia/airlines/get/bookingUsers/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<BookingUser> getBookingUsers() { return adminService.getAllBookingUsers(); }

    @RequestMapping(path = "utopia/airlines/post/bookingUsers", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveBookingUsers(@RequestBody List<BookingUser> bookingUsers) { adminService.saveBookingUsers(bookingUsers); }

    @RequestMapping(path = "utopia/airlines/get/flights/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<Flight> getFlights() { return adminService.getFlights(); }

    @RequestMapping(path = "utopia/airlines/post/flights", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveFlights(@RequestBody List<Flight> flights) { adminService.saveFlights(flights); }

    @RequestMapping(path = "utopia/airlines/get/flightBookings/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<FlightBookings> getFlightBookings() { return adminService.getFlightBookings(); }

    @RequestMapping(path = "utopia/airlines/post/flightBookings", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveFlightBookings(@RequestBody List<FlightBookings> flightBookings) { adminService.saveFlightBookings(flightBookings); }

    @RequestMapping(path = "utopia/airlines/get/passengers/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<Passenger> getPassengers() { return adminService.getPassengers(); }

    @RequestMapping(path = "utopia/airlines/post/passengers", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void savePassengers(@RequestBody List<Passenger> passengers) { adminService.savePassengers(passengers); }

    @RequestMapping(path = "utopia/airlines/get/routes/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<Route> getRoutes() { return adminService.getRoutes(); }

    @RequestMapping(path = "utopia/airlines/post/routes", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveRoutes(@RequestBody List<Route> routes) { adminService.saveRoutes(routes); }

    @RequestMapping(path = "utopia/airlines/get/users/dump", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public List<User> getUsers() {
        return adminService.getAllUsers();
    }

    @RequestMapping(path = "utopia/airlines/post/users", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void postUsers(@RequestBody List<User> users) {
        adminService.saveUsers(users);
    }

    @RequestMapping(path = "utopia/airlines/get/userRoles/dump", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public List<UserRole> getUserRoles() {
        return adminService.getAllUserRoles();
    }

    @RequestMapping(path = "utopia/airlines/post/userRoles", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void postUserRoles(@RequestBody List<UserRole> userRoles) {
        adminService.postUserRoles(userRoles);
    }

}
