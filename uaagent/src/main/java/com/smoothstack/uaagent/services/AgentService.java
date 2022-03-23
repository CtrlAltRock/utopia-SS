package com.smoothstack.uaagent.services;

import com.smoothstack.uaagent.models.Booking;
import com.smoothstack.uaagent.models.Flight;
import com.smoothstack.uaagent.models.Passenger;
import com.smoothstack.uaagent.models.User;
import com.smoothstack.uaagent.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgentService {

    @Autowired
    AirplaneRepository airplaneRepository;

    @Autowired
    AirplaneTypeRepository airplaneTypeRepository;

    @Autowired
    AirportRepository airportRepository;

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
    FlightRepository flightRepository;

    @Autowired
    FlightBookingsRepository flightBookingsRepository;

    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUserByUsername(String username) { return userRepository.findUserByUsername(username).get(); }

    public List<Flight> getFlights() {
        return flightRepository.findAll();
    }

    public List<Flight> getAvailableFlights() {
        List<Flight> flights = flightRepository.getAvailableFlights(LocalDateTime.now());
        return flights;
    }

    public Flight getFlightsById(Integer flightId) {
        Optional<Flight> flight = flightRepository.findById(flightId);
        if(flight.isPresent()) return flight.get();
        return null;
    }

    public List<Flight> getFlightsBetweenDates(LocalDateTime time1, LocalDateTime time2) {
        List<Flight> flights = flightRepository.findByDateRange(time1, time2);
        return flights;
    }


    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Integer bookingId) {
        Optional<Booking> bookings = bookingRepository.findById(bookingId);
        if(bookings.isPresent()) return bookings.get();
        return null;
    }

    public List<Booking> getBookingByIsActive(Boolean isActive) {
        List<Booking> bookings = bookingRepository.findByIsActive(isActive);
        return bookings;
    }

    // Should only be one booking by a confirmation code
    public List<Booking> getBookingByConfirmationCode(String confirmationCode) {
        List<Booking> bookings = bookingRepository.findByConfirmationCode(confirmationCode);
        return bookings;
    }

    public List<Passenger> getPassengers() {
        List<Passenger> passengers = passengerRepository.findAll();
        return passengers;
    }


    public Passenger getPassengerById(Integer id) {
        Optional<Passenger> passenger = passengerRepository.findById(id);
        if(passenger.isPresent()) {
            return passenger.get();
        }
        return null;
    }

    public void postPassenger(Passenger passenger) {
        passengerRepository.save(passenger);
    }

    public void patchPassenger(Passenger passenger) {
        passengerRepository.save(passenger);
    }

    public void deletePassenger(Integer id) {
        passengerRepository.deleteById(id);
    }


}
