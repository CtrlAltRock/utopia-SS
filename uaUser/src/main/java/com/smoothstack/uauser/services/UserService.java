package com.smoothstack.uauser.services;

import com.smoothstack.uauser.models.*;
import com.smoothstack.uauser.repos.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private AirplaneRepository airplaneRepository;

    @Autowired
    private AirplaneTypeRepository airplaneTypeRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingAgentRepository bookingAgentRepository;

    @Autowired
    private BookingGuestRepository bookingGuestRepository;

    @Autowired
    private BookingPaymentRepository bookingPaymentRepository;

    @Autowired
    private BookingUserRepository bookingUserRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightBookingsRepository flightBookingsRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
    public void deleteUser(User user) { userRepository.delete(user);}

    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if(user.isPresent()) return user.get();
        else return null;
    }

    public List<Flight> getAllFlights() {
        return (List<Flight>) flightRepository.findAll();
    }

    public Flight getFlightsById(Integer flightId) {

        Optional<Flight> flight = flightRepository.findById(flightId);
        if(flight.isPresent()) return flight.get();
        else return null;
    }

    public void addUserToFlight(Flight flight) {
        List<Passenger> passengers = passengerRepository.findAll();

        FlightBookings flightBooking = flightBookingsRepository.findFlightBookingsByFlightId(flight.getId());

        if (flightBooking != null){
            Booking booking = bookingRepository.findById(flightBooking.getFlightBookingsId().getBooking_id()).get();
            logger.info(booking.toString());
            Passenger passenger = new Passenger();
            passenger.setId(0);
            passenger.setBooking_id(booking);
            passenger.setGiven_name(user.getGiven_name());
            passenger.setFamily_name(user.getFamily_name());
            passenger.setDob(Date.valueOf(LocalDate.now().toString()));
            passenger.setGender("N/A");
            passenger.setAddress("N/A");

            BookingUser bookingUser = new BookingUser();
            BookingUserId bookingUserId = new BookingUserId();
            bookingUserId.setUser_id(Math.toIntExact(user.getId()));
            bookingUserId.setBooking_id(booking.getId());
            bookingUser.setBookingUserId(bookingUserId);

            if(flight.getReserved_seats() > 0) {
                flight.setReserved_seats(flight.getReserved_seats()-1);
                bookingUserRepository.save(bookingUser);
                flightRepository.save(flight);
                passengerRepository.save(passenger);
            }
            else throw new IllegalArgumentException("Not enough seats to add to");
        }

    }

    public void deleteUserFromFlight(Flight flight) {
        if(user != null) {
            FlightBookings flightBooking = flightBookingsRepository.findFlightBookingsByFlightId(flight.getId());
            if (flightBooking != null) {
                Booking booking = bookingRepository.findById(flightBooking.getFlightBookingsId().getBooking_id()).get();
                Passenger passenger = passengerRepository.findByNames(user.getFamily_name(), user.getGiven_name(), booking.getId());
                if(passenger == null) {
                    logger.info("here i guess");
                }
                else {
                    passenger.setBooking_id(null);
                    passengerRepository.delete(passenger);
                    flight.setReserved_seats(flight.getReserved_seats() + 1);
                    flightRepository.save(flight);
                }
            }
        }
    }

    public List<Flight> getUserFlights() {
        List<Flight> flights = flightRepository.findAllByUserId(user.getId());
        return flights;
    }

    public List<Flight> getAvailableFlights() {
        List<Flight> flights = flightRepository.getAvailableFlights(LocalDateTime.now());
        return flights;
    }

}
