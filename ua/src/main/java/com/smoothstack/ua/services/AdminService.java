package com.smoothstack.ua.services;

import com.smoothstack.ua.models.*;
import com.smoothstack.ua.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    AirplaneRepository airplaneRepository;

    @Autowired
    AirplaneTypeRepository airplaneTypeRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingAgentRepository bookingAgentRepository;

    @Autowired
    BookingGuestRepository bookingGuestRepository;

    @Autowired
    BookingPaymentRepository bookingPaymentRepository;

    @Autowired
    BookingUserRepository bookingUserRepository;

    @Autowired
    FlightBookingsRepository flightBookingsRepository;

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    RouteRepository routeRepository;

    public void saveUsers(List<User> users) {
        userRepository.saveAll(users);
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public List<Airport> getAllAirports() {
        return (List<Airport>) airportRepository.findAll();
    }

    public void saveAirports(List<Airport> airports) {
        airportRepository.saveAll(airports);
    }

    public List<Airplane> getAllAirplanes() {
        return (List<Airplane>) airplaneRepository.findAll();
    }

    public void saveAirplanes(List<Airplane> airplanes) {
        airplaneRepository.saveAll(airplanes);
    }

    public List<AirplaneType> getALLAirplaneTypes() {
        return (List<AirplaneType>) airplaneTypeRepository.findAll();
    }

    public void saveAirplaneTypes(List<AirplaneType> airplaneTypes) {
        airplaneTypeRepository.saveAll(airplaneTypes);
    }

    public List<Booking> getAllBookings() {
        return (List<Booking>) bookingRepository.findAll();
    }

    public void saveBookings(List<Booking> bookings) {
        bookingRepository.saveAll(bookings);
    }

    public List<UserRole> getAllUserRoles() {
        return (List<UserRole>) userRoleRepository.findAll();
    }

    public void postUserRoles(List<UserRole> userRoles) {
        userRoleRepository.saveAll(userRoles);
    }

    public List<BookingAgent> getALlBookingAgents() { return (List<BookingAgent>) bookingAgentRepository.findAll(); }

    public void saveBookingAgents(List<BookingAgent> bookingAgents) { bookingAgentRepository.saveAll(bookingAgents); }

    public List<BookingPayment> getAllBookingPayments() { return (List<BookingPayment>) bookingPaymentRepository.findAll(); }

    public void saveBookingPayment(List<BookingPayment> bookingPayments) { bookingPaymentRepository.saveAll(bookingPayments); }

    public List<BookingUser> getAllBookingUsers() {
        return (List<BookingUser>) bookingUserRepository.findAll();
    }

    public void saveBookingUsers(List<BookingUser> bookingUsers) {
        bookingUserRepository.saveAll(bookingUsers);
    }

    public List<BookingGuest> getAllBookingGuests() { return (List<BookingGuest>) bookingGuestRepository.findAll(); }

    public void saveBookingGuests(List<BookingGuest> bookingGuests) { bookingGuestRepository.saveAll(bookingGuests); }

    public List<FlightBookings> getFlightBookings() { return (List<FlightBookings>) flightBookingsRepository.findAll(); }

    public void saveFlightBookings(List<FlightBookings> flightBookings) { flightBookingsRepository.saveAll(flightBookings); }

    public List<Flight> getFlights() { return (List<Flight>) flightRepository.findAll(); }

    public void saveFlights(List<Flight> flights) { flightRepository.saveAll(flights); }

    public List<Passenger> getPassengers() {
        return (List<Passenger>) passengerRepository.findAll();
    }

    public void savePassengers(List<Passenger> passengers) {
        passengerRepository.saveAll(passengers);
    }

    public List<Route> getRoutes() {
        return (List<Route>) routeRepository.findAll();
    }

    public void saveRoutes(List<Route> routes) {
        routeRepository.saveAll(routes);
    }
}
